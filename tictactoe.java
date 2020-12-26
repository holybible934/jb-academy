package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        final int size = 3;
        char[][] arr = new char[size][size];
        readNineChars(arr, size);
        printBoard(size, arr);
        startGame(arr, size);
    }

    private static void printBoard(int size, char[][] arr) {
        System.out.println("---------");
        for (int i = 0; i < size; i++) {
            System.out.print("| ");
            for (char output : arr[i]) {
                System.out.print(output + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void readNineChars(char[][] arr, int size) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = ' ';
//                if (input.charAt(i*size+j) == 'O' || input.charAt(i*size+j) == 'X') {
//                    arr[i][j] = input.charAt(i * size + j);
//                }
//                else {
//                    arr[i][j] = ' ';
//                }
            }
        }
    }

    private static boolean judgeGameResult(char[][] arr, int size) {
        boolean XWins = false, OWins = false;
        int Ocount = 0, Xcount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr[i][j] == 'O') {
                    Ocount++;
                }
                else if (arr[i][j] == 'X') {
                    Xcount++;
                }
            }
        }
        // If the difference is 2 or more, then the game state is impossible
//        if ((Ocount - Xcount < -1) || (Ocount - Xcount) > 1) {
//            System.out.println("Impossible");
//            return;
//        }
        // The same in a row
        for (int i = 0; i < size; i++) {
            if ((arr[i][0] == arr[i][1]) && (arr[i][1] == arr[i][2])) {
                if (arr[i][0] == 'O') {
                    OWins = true;
                    System.out.println("O wins");
                }
                else if (arr[i][0] == 'X') {
                    XWins = true;
                    System.out.println("X wins");
                }
            }
        }
        // The same in a column
        for (int j = 0; j < size; j++) {
            if ((arr[0][j] == arr[1][j]) && (arr[1][j] == arr[2][j])) {
                if (arr[0][j] == 'O') {
                    OWins = true;
                    System.out.println("O wins");
                }
                else if (arr[0][j] == 'X') {
                    XWins = true;
                    System.out.println("X wins");
                }
            }
        }
        // (0, 0) && (1, 1) && (2, 2)
        if ((arr[0][0] == arr[1][1]) && (arr[1][1] == arr[2][2])) {
            if (arr[0][0] == 'O') {
                OWins = true;
                System.out.println("O wins");
            }
            else if (arr[0][0] == 'X') {
                XWins = true;
                System.out.println("X wins");
            }
        }

        // (0, 2) && (1, 1) && (2, 0)
        if ((arr[0][2] == arr[1][1]) && (arr[1][1] == arr[2][0])) {
            if (arr[0][2] == 'O') {
                OWins = true;
                System.out.println("O wins");
            }
            else if (arr[0][2] == 'X') {
                XWins = true;
                System.out.println("X wins");
            }
        }

        // When the grid has three X’s in a row as well as three O’s in a row
//        if (XWins && OWins) {
//            System.out.println("Impossible");
//        }
        // Neither side has three in a row but the grid still has empty cells
        else if (!XWins && !OWins) {
            //                System.out.println("Game not finished");
            if (Xcount + Ocount == 9) {
                System.out.println("Draw");
                return true;
            }
        }
        return XWins ? XWins : OWins;
    }

    private static void startGame(char[][] arr, int size) {
        Scanner scanner = new Scanner(System.in);
        int columnIndex, rowIndex;
        boolean gameSet = false, XTurn = true;
        while (!gameSet) {
            if (scanner.hasNextInt()) {
                rowIndex= scanner.nextInt() - 1;
                columnIndex = scanner.nextInt() - 1;
                if (columnIndex >= 0 && columnIndex <= 2
                        && rowIndex >= 0 && rowIndex <= 2) {
                    if (arr[rowIndex][columnIndex] == ' ') {
                        arr[rowIndex][columnIndex] = XTurn ? 'X' : 'O';
                        XTurn = !XTurn;
                        printBoard(size, arr);
                        gameSet = judgeGameResult(arr, size);
                    }
                    else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                }
                else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            }
            else {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        }
    }
}
