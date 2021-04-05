package processor;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rowOfMatrixA = scanner.nextInt();
        int columnOfMatrixA = scanner.nextInt();
        scanner.nextLine();
        int[][] matrixA = new int[rowOfMatrixA][columnOfMatrixA];
        for (int i = 0; i< rowOfMatrixA; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixA[i] = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();
        }
//        doMaxtrixAddition(scanner, rowOfMatrixA, columnOfMatrixA, matrixA);
        doMultiplyConstant(scanner, rowOfMatrixA, matrixA);
    }

    private static void doMultiplyConstant(Scanner scanner, int rowOfMatrixA, int[][] matrixA) {
        int cons = scanner.nextInt();
        for (int i = 0; i< rowOfMatrixA; i++) {
            matrixA[i] = Arrays.stream(matrixA[i])
                    .map(e -> e * cons)
                    .toArray();
        }
        for (int i = 0; i< rowOfMatrixA; i++) {
            Arrays.stream(matrixA[i]).forEach(e -> System.out.print(e + " "));
            System.out.println();
        }
    }

    private static void doMaxtrixAddition(Scanner scanner, int rowOfMatrixA, int columnOfMatrixA, int[][] matrixA) {
        int rowOfMatrixB = scanner.nextInt();
        int columnOfMatrixB = scanner.nextInt();
        scanner.nextLine();
        int[][] matrixB = new int[rowOfMatrixB][columnOfMatrixB];
        for (int i = 0; i< rowOfMatrixB; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixB[i] = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();
        }
        if (rowOfMatrixA != rowOfMatrixB || columnOfMatrixA != columnOfMatrixB) {
            System.out.println("ERROR");
        } else {
            for (int i = 0; i < rowOfMatrixA; i++) {
                for (int j = 0; j < columnOfMatrixA; j++) {
                    System.out.print(matrixA[i][j] + matrixB[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}
