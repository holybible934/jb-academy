import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int counter = scanner.nextInt();
        while (counter-- > 0) {
            int input = scanner.nextInt();
            if (input % 6 == 0) {
                sum += input;
            }
        }
        System.out.println(sum);
    }
}