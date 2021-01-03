import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int peanutButterCups = scanner.nextInt();
        boolean isWeekend = scanner.nextBoolean();
        boolean isPartySuccessful = false;
        if (isWeekend) {
            if (peanutButterCups >= 15 && peanutButterCups <= 25) {
                isPartySuccessful = true;
            }
        } else {
            if (peanutButterCups >= 10 && peanutButterCups <= 20) {
                isPartySuccessful = true;
            }
        }
        System.out.println(isPartySuccessful);
    }
}