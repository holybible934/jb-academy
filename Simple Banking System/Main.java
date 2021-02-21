package banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int USER_COUNT = 0;
    private static Connection conn;
    private static String fileName = "myCards.s3db";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Card> myUsers = new ArrayList<>();
        loadDatabase(args);
        printMainMenu();
        while (scanner.hasNextLine()) {
            int cmd = Integer.parseInt(scanner.nextLine());
            switch (cmd) {
                case 1:
                    myUsers.add(createUser());
                    break;
                case 2:
                    if (myUsers.size() == 0) {
                        System.out.println("No any user yet");
                    } else {
                        Card loggonUser = userLogin(scanner, myUsers);
                        while (loggonUser != null) {
                            printLoggonMenu();
                            cmd = Integer.parseInt(scanner.nextLine());
                            switch (cmd) {
                                case 1:
                                    System.out.println("Balance: " + loggonUser.getBalance());
                                    break;
                                case 2:
                                    loggonUser = null;
                                    System.out.println("You have successfully logged out!");
                                    break;
                                case 0:
                                    return;
                                default:
                            }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
    }

    private static Card createUser() {
        String carNum = luhnAlgorithm();
        String myPin = String.format("%04d", (int) (Math.random() * 10000) % 10000);
        Card myUser = new Card(carNum, myPin);
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
            pstmt.setString(2, myUser.getmCardNumber());
            pstmt.setString(3, myUser.getmPIN());
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("User is stored");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static String luhnAlgorithm() {
        long[] cardNum = new long[16];
        String BIN = "400000";
        for (int i = 0; i < BIN.length(); i++) {
            cardNum[i] = Integer.parseInt(String.valueOf(BIN.toCharArray()[i]));
        }
        long userCount = USER_COUNT;
        for (int i = cardNum.length - 2; i >= BIN.length(); i--) {
            cardNum[i] = userCount % 10;
            userCount /= 10;
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

    private static Card userLogin(Scanner scanner, List<Card> myUsers) {
        System.out.println("Enter your card number:");
        String inputCardNum = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String inputPin = scanner.nextLine();
        Card logonUser = null;
        for (Card user : myUsers) {
            if (user.getUserByCardNum(inputCardNum)) {
                logonUser = user;
            }
        }
        if (logonUser != null) {
            if (logonUser.verifyPin(inputPin)) {
                System.out.println("You have successfully logged in!");
                return logonUser;
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
        return logonUser;
    }

    private static void printLoggonMenu() {
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
    }

    private static void printMainMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

}