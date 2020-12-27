import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();
        int count = 0;
        for (int i = 0; i < 1001; i++) {
            if (a * i * i * i + b * i * i + c * i + d == 0) {
                System.out.println(i);
                count++;
            }
            if (count > 3) {
                break;
            }
        }
    }
}