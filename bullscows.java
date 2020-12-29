package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        int digits =  scanner.nextInt();
        if (digits > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            return;
        }
        scanner.nextLine();
        String answer = "";
        StringBuilder secretCode = new StringBuilder();
        generateSecretCode(digits, secretCode);
        System.out.println("Okay, let's start a game!");
        int turns = 1;

        eachTurnOfPlay(scanner, answer, secretCode, turns);
    }

    private static void eachTurnOfPlay(Scanner scanner, String answer, StringBuilder secretCode, int turns) {
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
            else if (bulls == 4) {
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

    private static void generateSecretCode(int digits, StringBuilder code) {
        while (digits > 0) {
            int digit = (int) (System.nanoTime() % 10);
            if (!code.toString().contains(String.valueOf(digit))) {
                code.append(digit);
                digits--;
            }
        }
    }
}
