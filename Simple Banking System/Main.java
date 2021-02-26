package banking;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int USER_COUNT = 0;
    private static Connection conn;
    private static String fileName = "myCards.s3db";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadDatabase(args);
        printMainMenu();
        while (scanner.hasNextLine()) {
            int cmd = Integer.parseInt(scanner.nextLine());
            switch (cmd) {
                case 1:
                    createUser();
                    break;
                case 2:
                    Card signInUser = userLogin(scanner);
                    while (signInUser != null) {
                        printSignInMenu();
                        cmd = Integer.parseInt(scanner.nextLine());
                        switch (cmd) {
                            case 1:
                                System.out.println("Balance: " + signInUser.getBalance());
                                break;
                            case 2:
                                System.out.println("Input your deposit amount:");
                                signInUser = addIncome(signInUser, Integer.parseInt(scanner.nextLine()));
                                break;
                            case 4:
                                closeTheCard(signInUser);
                                USER_COUNT--;
                                signInUser = null;
                                System.out.println("You have successfully closed your account and logged out!");
                                break;
                            case 5:
                                signInUser = null;
                                System.out.println("You have successfully logged out!");
                                break;
                            case 0:
                                return;
                            default:
                        }
                    }
                    break;
                case 0:
                    System.out.println("Bye");
                    return;
                default:
            }
            printMainMenu();
        }
    }

    private static Card addIncome(Card user, int increment) {
        operateBalance(user.getCardNumber(), user.getBalance() + increment);
        return reloadUser(user.getCardNumber());
    }

    private static Card reloadUser(String cardNumber) {
        String sql = "SELECT * FROM card WHERE number = ?;";
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cardNumber);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Card user = null;
            if (rs != null) {
                user = new Card(rs.getString("number"), rs.getString("pin"), rs.getInt("balance"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void operateBalance(String carNum, int balance) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, balance);
            pstmt.setString(2, carNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void closeTheCard(Card signInUser) {
        String sql = "DELETE FROM card WHERE number = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, signInUser.getCardNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadDatabase(String[] args) {
        String url = "jdbc:sqlite:";
        if (Arrays.asList(args).contains("-fileName")) {
            fileName = args[1];
        }
        try {
            conn = DriverManager.getConnection(url + fileName);
            createTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card(\n" +
                "id INTEGER,\n" +
                "number TEXT,\n" +
                "pin TEXT,\n" +
                "balance INTEGER DEFAULT 0)";
        try {
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static Card createUser() {
        String carNum = luhnAlgorithm();
        String myPin = String.format("%04d", (int) (Math.random() * 10000) % 10000);
        Card myUser = new Card(carNum, myPin, 0);
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + carNum + "\n" +
                "Your card PIN:\n" + myPin);
        storeUser(myUser);
        return myUser;
    }

    private static void storeUser(Card myUser) {
        String sql = "INSERT INTO card(id, number, pin) \n" +
                "VALUES(?, ?, ?);";
        try {
            String url = "jdbc:sqlite:";
            conn = DriverManager.getConnection(url + fileName);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, USER_COUNT);
            pstmt.setString(2, myUser.getCardNumber());
            pstmt.setString(3, myUser.getmPIN());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String luhnAlgorithm() {
        long[] cardNum = new long[16];
        String BIN = "400000";
        for (int i = 0; i < BIN.length(); i++) {
            cardNum[i] = Integer.parseInt(String.valueOf(BIN.toCharArray()[i]));
        }
        long accountIdentifier = (long) (Math.random() * 100000) % 100000;
        for (int i = cardNum.length - 2; i >= BIN.length(); i--) {
            cardNum[i] = accountIdentifier % 10;
            accountIdentifier /= 10;
        }
        long[] copiedCardNum = cardNum.clone();
        for (int i = 0; i < cardNum.length - 1; i += 2) {
            copiedCardNum[i] *= 2;
        }
        long checkSum = 0;
        for (long num : copiedCardNum) {
            if (num >= 10) {
                num -= 9;
            }
            checkSum += num;
        }
        while (checkSum > 0) {
            checkSum -= 10;
        }
        cardNum[15] = Math.abs(checkSum);
        StringBuilder cardNumStr = new StringBuilder();
        Arrays.stream(cardNum).forEach(cardNumStr::append);
        USER_COUNT++;
        return cardNumStr.toString();
    }

    private static Card userLogin(Scanner scanner) {
        System.out.println("Enter your card number:");
        String inputCardNum = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String inputPin = scanner.nextLine();
        Card logonUser = null;

        logonUser = getUser(inputCardNum, logonUser);

        if (logonUser != null) {
            if (logonUser.verifyPin(inputPin)) {
                System.out.println("You have successfully logged in!");
                return logonUser;
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
        return null;
    }

    private static Card getUser(String inputCardNum, Card logonUser) {
        String sql = "SELECT * FROM card WHERE number = ?";
        ResultSet rs = null;
        try {
//            String url = "jdbc:sqlite:";
//            conn = DriverManager.getConnection(url + fileName);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputCardNum);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                logonUser = new Card(inputCardNum, rs.getString("pin"), rs.getInt("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logonUser;
    }

    private static void printSignInMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
    }

    private static void printMainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

}