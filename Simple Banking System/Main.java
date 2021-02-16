package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int ACCOUNT_IDENTIFIER;
    private static String BIN = "400000";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<BankingUser> myUsers = new ArrayList<>();
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
                        BankingUser loggonUser = userLogin(scanner, myUsers);
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

    private static BankingUser createUser() {
        String carNum = BIN + String.format("%010d", ACCOUNT_IDENTIFIER++);
        String myPin = String.format("%04d", (int) (Math.random() * 10000) % 10000);
        BankingUser myUser = new BankingUser(carNum, myPin);
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + carNum + "\n" +
                "Your card PIN:\n" + myPin);
        return myUser;
    }

    private static BankingUser userLogin(Scanner scanner, List<BankingUser> myUsers) {
        System.out.println("Enter your card number:");
        String inputCardNum = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String inputPin = scanner.nextLine();
        BankingUser logonUser = null;
        for (BankingUser user : myUsers) {
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