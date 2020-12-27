package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("The secret code is prepared: ****.");

        int turns = 1;
        String answer = "", code = "9305";

        Scanner scanner = new Scanner(System.in);
        while (answer.compareTo(code) != 0) {
            System.out.println("Turn " + turns +". Answer:");
            answer = scanner.nextLine();
            int cows = 0, bulls = 0;
            for (int i = 0; i < answer.toCharArray().length; i++) {
                char ch = answer.charAt(i);
                for (int j = 0; j < code.toCharArray().length; j++) {
                    if (ch == code.charAt((i))) {
                        bulls++;
                        break;
                    }
                    else if (ch == code.charAt(j)) {
                        cows++;
                        break;
                    }
                }
            }
            if (cows == 0 && bulls == 0) {
                System.out.println("Grade: None." + "The secret code is " + code + ".");
            }
            else if (bulls == 4) {
                System.out.println("Grade: " + bulls + " bulls.");
                System.out.println("Congrats! The secret code is " + code + ".");
            }
            else if (bulls == 0) {
                System.out.println("Grade: " + cows + " cow(s)." + "The secret code is " + code + ".");
            }
            else if (cows == 0) {
                System.out.println("Grade: " + bulls + " bull(s)." + "The secret code is " + code + ".");
            }
            else {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)." + "The secret code is " + code + ".");
            }
            turns++;
        }
    }
}
