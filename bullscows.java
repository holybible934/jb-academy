package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);
        int digits = scanner.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int range = scanner.nextInt();
        scanner.nextLine();
        String answer = "";
        StringBuilder secretCode = new StringBuilder();
        generateSecretCode(digits, range, secretCode);
        System.out.println("Okay, let's start a game!");
        eachTurnOfPlay(scanner, answer, secretCode, digits);
    }

    private static void eachTurnOfPlay(Scanner scanner, String answer, StringBuilder secretCode, int digits) {
        int turns = 1;
        while (answer.compareTo(secretCode.toString()) != 0) {
            System.out.println("Turn " + turns +". Answer:");
            answer = scanner.nextLine();
            int cows = 0, bulls = 0;
            for (int i = 0; i < answer.length(); i++) {
                char ch = answer.charAt(i);
                for (int j = 0; j < secretCode.length(); j++) {
                    if (ch == secretCode.charAt((i))) {
                        bulls++;
                        break;
                    }
                    else if (ch == secretCode.charAt(j)) {
                        cows++;
                        break;
                    }
                }
            }
            if (cows == 0 && bulls == 0) {
                System.out.println("Grade: None.");
            }
            else if (bulls == digits) {
                System.out.println("Grade: " + bulls + " bulls.");
                System.out.println("Congratulations! You guessed the secret code.");
            }
            else if (bulls == 0) {
                System.out.println("Grade: " + cows + " cow(s).");
            }
            else if (cows == 0) {
                System.out.println("Grade: " + bulls + " bull(s).");
            }
            else {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s).");
            }
            turns++;
        }
    }

    private static void generateSecretCode(int digits, int range, StringBuilder code) {
        char[] fullSet = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        System.out.print("The secret is prepared: "+ "*".repeat(digits));
        for (int i = 0; i < digits; i++) {
            System.out.print("*");
        }
        if (range > 10) {
            System.out.println(" (0-9, a-" + fullSet[range - 1] + ").");
        }
        else {
            System.out.println(" (0-" + fullSet[range - 1] + ").");
        }
        while (digits > 0) {
            int index = (int) (Math.random() * 100.0 % range);
            if (!code.toString().contains(String.valueOf(fullSet[index]))) {
                code.append(fullSet[index]);
                digits--;
            }
        }
    }
}
