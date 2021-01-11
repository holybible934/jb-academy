import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        StringBuilder sb = new StringBuilder(scanner.nextLine());
        int j = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
        StringBuilder check = new StringBuilder(scanner.nextLine());
        check.deleteCharAt(1);
        System.out.println(!sb.toString().contains(check.toString())
                && !sb.toString().contains(check.reverse().toString()));
    }
}