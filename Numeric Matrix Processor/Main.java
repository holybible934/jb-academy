package processor;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int choice;
        choice = getChoice(scanner);
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
                case 4:
                    doMatrixTranspose(scanner);
                    break;
                case 5:
                    doMatrixDeterminant(scanner);
                    break;
                case 6:
                    doInverseMatrix(scanner);
                    break;
                default:
                    break;
            }
            printMenu();
            choice = getChoice(scanner);
        }
    }

    private static void doInverseMatrix(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        double[][] inverse = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                inverse[i][j] = Math.pow(-1, i + j)
                        * determinant(minor(matrix, i, j));

        // adjugate and determinant
        double det = 1.0 / determinant(matrix);
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = inverse[i][j];
                inverse[i][j] = inverse[j][i] * det;
                inverse[j][i] = temp * det;
            }
        }
        printMatrix(inverse);
    }

    private static double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }


    private static int getChoice(Scanner scanner) {
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Wrong Input");
            choice = -1;
        }
        return choice;
    }

    private static void doMatrixDeterminant(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        System.out.println("The result is:\n" + determinant(matrix));
    }

    public static double determinant(double[][] matrix) {
        double determinant = 0;
        if (matrix.length == 2) {
            return crossMultiply(matrix);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                continue;
            }
            determinant += Math.pow(-1, i) * matrix[0][i] * determinant(createSubMatrix(matrix, i));
        }
        return determinant;
    }

    public static double crossMultiply(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
    }

    public static double[][] createSubMatrix(double[][] matrix, int col) {
        double[][] subMatrix = new double[matrix.length - 1][matrix.length - 1];
        int rowIndex = 0;
        for (int i = 1; i < matrix.length; i++) {
            int colIndex = 0;
            for (int k = 0; k < matrix[0].length; k++) {
                if (k == col) {
                    continue;
                }
                subMatrix[rowIndex][colIndex] = matrix[i][k];
                colIndex++;
            }
            rowIndex++;

        }
        return subMatrix;
    }

    private static void doMatrixTranspose(Scanner scanner) {
        printTransposeMenu();
        int choice;
        choice = getChoice(scanner);
        switch (choice) {
            case 1:
                transposeMainDiag(scanner);
                break;
            case 2:
                transposeSideDiag(scanner);
                break;
            case 3:
                transposeVertical(scanner);
                break;
            case 4:
                transposeHorizontal(scanner);
                break;
            default:
                break;
        }
    }

    private static void transposeHorizontal(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = 0; j < matrix.length; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - 1 - i][j];
                matrix[matrix.length - 1 - i][j] = temp;
            }
        }
        printMatrix(matrix);
    }

    private static void transposeVertical(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length / 2; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length - 1 - j];
                matrix[i][matrix.length - 1 - j] = temp;
            }
        }
        printMatrix(matrix);
    }

    private static void transposeSideDiag(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length - 1 - i; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - 1 - j][matrix.length - 1 - i];
                matrix[matrix.length - 1 - j][matrix.length - 1 - i] = temp;
            }
        }
        printMatrix(matrix);
    }

    private static void printMatrix(double[][] matrix) {
        System.out.println("The result is:");
        for (double[] row : matrix) {
            for (double v : row) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    private static void transposeMainDiag(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        double[][] matrix = getMatrix(scanner);
        System.out.println("Enter matrix:");
        initializeMatrix(scanner, matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        printMatrix(matrix);
    }

    private static void printTransposeMenu() {
        System.out.print("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line");
        System.out.print("Your choice: ");
    }

    private static void doMultiplyByMatrices(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        double[][] matrixA = getMatrix(scanner);
        System.out.println("Enter first matrix:");
        initializeMatrix(scanner, matrixA);
        System.out.print("Enter size of second matrix: ");
        double[][] matrixB = getMatrix(scanner);
        System.out.println("Enter second matrix:");
        initializeMatrix(scanner, matrixB);
        if (matrixA[0].length != matrixB.length) {
            System.out.println("The operation cannot be performed.");
        } else {
            double[][] resultMatrix = new double[matrixA.length][matrixB[0].length];
            for (int i = 0; i< matrixA.length; i++) {
                Arrays.stream(resultMatrix[i]).forEach(e -> e = 0.0);
            }
            System.out.println("The result is:");
            for (int i = 0; i < matrixA.length; i++) {
                for (int j = 0; j < matrixB[0].length; j++) {
                    for (int k = 0; k < matrixA[0].length; k++) {
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                    System.out.print(resultMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    private static double[][] getMatrix(Scanner scanner) {
        int rowOfMatrixA = scanner.nextInt();
        int columnOfMatrixA = scanner.nextInt();
        scanner.nextLine();
        return new double[rowOfMatrixA][columnOfMatrixA];
    }

    private static void printMenu() {
        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit");
        System.out.print("Your choice: ");
    }

    private static void doMultiplyConstant(Scanner scanner) {
        double[][] matrixA = getMatrix(scanner);
        initializeMatrix(scanner, matrixA);
        double cons = Double.parseDouble(scanner.nextLine());
        System.out.println("The result is:");
//        for (int i = 0; i< rowOfMatrixA; i++) {
//            matrixA[i] = Arrays.stream(matrixA[i]).map(e -> e * cons).toArray();
//        }
        for (double[] doubles : matrixA) {
            Arrays.stream(doubles).forEach(e -> System.out.print(e * cons + " "));
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeMatrix(Scanner scanner, double[][] matrixA) {
        for (int i = 0; i < matrixA.length; i++) {
            String[] input = scanner.nextLine().split(" ");
            matrixA[i] = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
        }
    }

    private static void doMatrixAddition(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");
        double[][] matrixA = getMatrix(scanner);
        System.out.println("Enter first matrix:");
        initializeMatrix(scanner, matrixA);
        System.out.print("Enter size of second matrix: ");
        double[][] matrixB = getMatrix(scanner);
        System.out.println("Enter second matrix:");
        initializeMatrix(scanner, matrixB);
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
            for (int i = 0; i < matrixA.length; i++) {
                for (int j = 0; j < matrixA[0].length; j++) {
                    System.out.print(matrixA[i][j] + matrixB[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}
