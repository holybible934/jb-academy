import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] inputLines = scanner.nextLine().split(" ");
        long a = Long.parseLong(inputLines[0]);
        long b = Long.parseLong(inputLines[2]);
        switch (inputLines[1]) {
            case "+":
                System.out.println(a + b);
                break;
            case "-":
                System.out.println(a - b);
                break;
            case "*":
                System.out.println(a * b);
                break;
            case "/":
                if (b != 0) {
                    System.out.println(a / b);
                } else {
                    System.out.println("Division by 0!");
                }
                break;
            default:
                System.out.println("Unknown operator");
                break;
        }
    }
}