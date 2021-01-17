import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int arrSize = scanner.nextInt();
        int[] arr = new int[arrSize];
        for (int i = 0; i < arrSize; i++) {
            arr[i] = scanner.nextInt();
        }
        int n = scanner.nextInt();
        int sum = 0;
        for (int element : arr) {
            if (element > n) {
                sum += element;
            }
        }
        System.out.println(sum);
    }
}