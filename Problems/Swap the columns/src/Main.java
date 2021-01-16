import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < m; l++) {
                matrix[k][l] = scanner.nextInt();
            }
        }
        final int i = scanner.nextInt();
        final int j = scanner.nextInt();
        for (int row = 0; row < n; row++) {
            int temp = matrix[row][i];
            matrix[row][i] = matrix[row][j];
            matrix[row][j] = temp;
        }
        for (int[] arr : matrix) {
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}