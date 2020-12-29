import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        long input = scanner.nextLong();
        long result = 0L;
        while (input != 0 && result < 1000) {
            result += input;
            input = scanner.nextLong();
        }
        if (result >= 1000) {
            System.out.println(result - 1000);
        } else {
            System.out.println(result);
        }
    }
}