import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int first = scanner.nextInt();
        int second = scanner.nextInt();
        int temp = first;
        first = second;
        second = temp;
        System.out.printf("%d %d", first, second);
    }
}