package processor;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice != 0) {
            switch (choice) {
                case 1:
                    doMatrixAddition(scanner);
                    break;
                case 2:
                    doMultiplyConstant(scanner);
                    break;
                case 3:
                    doMultiplyByMatrices(scanner);
                    break;
                default:
                    break;
            }
            printMenu();
            System.out.print("Your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong Input");
                choice = -1;
            }
        }
    }

    private static void doMultiplyByMatrices(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        int rowOfMatrixA = scanner.nextInt();
        int columnOfMatrixA = scanner.nextInt();
        scanner.nextLine();
        double[][] matrixA = new double[rowOfMatrixA][columnOfMatrixA];
        System.out.println("Enter first matrix:");
        for (int i = 0; i< rowOfMatrixA; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixA[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
        System.out.print("Enter size of second matrix: ");
        int rowOfMatrixB = scanner.nextInt();
        int columnOfMatrixB = scanner.nextInt();
        scanner.nextLine();
        double[][] matrixB = new double[rowOfMatrixB][columnOfMatrixB];
        System.out.println("Enter second matrix:");
        for (int i = 0; i< rowOfMatrixB; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixB[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
        if (columnOfMatrixA != rowOfMatrixB) {
            System.out.println("The operation cannot be performed.");
        } else {
            double[][] resultMatrix = new double[rowOfMatrixA][columnOfMatrixB];
            for (int i = 0; i< rowOfMatrixA; i++) {
                Arrays.stream(resultMatrix[i]).forEach(e -> e = 0.0);
            }
            System.out.println("The result is:");
            for (int i = 0; i < rowOfMatrixA; i++) {
                for (int j = 0; j < columnOfMatrixB; j++) {
                    for (int k = 0; k < columnOfMatrixA; k++) {
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                    System.out.print(resultMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "0. Exit");
    }

    private static void doMultiplyConstant(Scanner scanner) {
        int rowOfMatrixA = scanner.nextInt();
        int columnOfMatrixA = scanner.nextInt();
        scanner.nextLine();
        double[][] matrixA = new double[rowOfMatrixA][columnOfMatrixA];
        for (int i = 0; i< rowOfMatrixA; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixA[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
        double cons = Double.parseDouble(scanner.nextLine());
        System.out.println("The result is:");
//        for (int i = 0; i< rowOfMatrixA; i++) {
//            matrixA[i] = Arrays.stream(matrixA[i])
//                    .map(e -> e * cons)
//                    .toArray();
//        }
        for (int i = 0; i< rowOfMatrixA; i++) {
            Arrays.stream(matrixA[i]).forEach(e -> System.out.print(e * cons + " "));
            System.out.println();
        }
        System.out.println();
    }

    private static void doMatrixAddition(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        int rowOfMatrixA = scanner.nextInt();
        int columnOfMatrixA = scanner.nextInt();
        scanner.nextLine();
        double[][] matrixA = new double[rowOfMatrixA][columnOfMatrixA];
        System.out.println("Enter first matrix:");
        for (int i = 0; i< rowOfMatrixA; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixA[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
        System.out.print("Enter size of second matrix: ");
        int rowOfMatrixB = scanner.nextInt();
        int columnOfMatrixB = scanner.nextInt();
        scanner.nextLine();
        double[][] matrixB = new double[rowOfMatrixB][columnOfMatrixB];
        System.out.println("Enter second matrix:");
        for (int i = 0; i< rowOfMatrixB; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixB[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
        if (rowOfMatrixA != rowOfMatrixB || columnOfMatrixA != columnOfMatrixB) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
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
