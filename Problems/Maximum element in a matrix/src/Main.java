import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int[][] arrays = new int[rows][columns];
        int maxRowIndex = 0;
        int maxColumnIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arrays[i][j] = scanner.nextInt();
                if (arrays[i][j] > arrays[maxRowIndex][maxColumnIndex]) {
                    maxRowIndex = i;
                    maxColumnIndex = j;
                }
            }
        }
        System.out.printf("%d %d", maxRowIndex, maxColumnIndex);
    }
}