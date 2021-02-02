import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        int[] arr1 = new int[n];
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }
        final int k = scanner.nextInt();
        int[] arr2 = new int[k];
        for (int i = 0; i < k; i++) {
            arr2[i] = scanner.nextInt();
        }
        for (int target : arr2) {
            int result = Arrays.binarySearch(arr1, target);
            if (result >= 0) {
                System.out.printf("%d ", result + 1);
            } else {
                System.out.printf("%d ", -1);
            }
        }
    }
}