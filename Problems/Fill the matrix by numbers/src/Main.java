import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int size = scanner.nextInt();
        int[][] arr = new int[size][size];
        for (int i = 0; i < size; i++) {
            int value = 0;
            for (int j = i; j < size; j++) {
                arr[i][j] = value++;
            }
            for (int j = 0; j < i; j++) {
                arr[i][j] = arr[j][i];
            }
        }
        for (int[] row : arr) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}