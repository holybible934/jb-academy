import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder(scanner.nextLine());
        if (sb.length() % 2 == 0) {
            sb.deleteCharAt(sb.length() / 2);
            sb.deleteCharAt(sb.length() / 2);
        } else {
            sb.deleteCharAt(sb.length() / 2);
        }
        System.out.println(sb.toString());
    }
}