import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        char[] arr = scanner.nextLine().toCharArray();
        boolean orderedInEng = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i + 1] != arr[i] + 1) {
                orderedInEng = false;
                break;
            }
        }
        System.out.println(orderedInEng);
    }
}