package battleship;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialize battle field
        final int border = 10;
        char[][] battleField = new char[border][border];
        for (int i = 0; i < border; i++) {
            for (int j = 0; j < border; j++) {
                battleField[i][j] = '~';
            }
        }
        printBattleField(border, battleField);

        // Take Position
        Scanner scanner = new Scanner(System.in);
        int allocPhase = 0;
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        String[] buf = scanner.nextLine().toUpperCase(Locale.ROOT).split(" ");
        while (allocPhase < 5) {
            switch (allocPhase) {
                case 0:
                    allocPhase = placeAircraftCarrier(battleField, buf);
                    if (allocPhase == 1) {
                        System.out.println("Enter the coordinates of the Battleship (4 cells):");
                    }
                    break;
                case 1:
                    allocPhase = placeBattleship(battleField, buf);
                    if (allocPhase == 2) {
                        System.out.println("Enter the coordinates of the Submarine (3 cells):");
                    }
                    break;
                case 2:
                    allocPhase = placeSubmarine(battleField, buf);
                    if (allocPhase == 3) {
                        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
                    }
                    break;
                case 3:
                    allocPhase = placeCruiser(battleField, buf);
                    if (allocPhase == 4) {
                        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
                    }
                    break;
                case 4:
                    allocPhase = placeDestroyer(battleField, buf);
                    break;
                default:
            }
            buf = scanner.nextLine().toUpperCase(Locale.ROOT).split(" ");
        }
    }

    private static int placeDestroyer(char[][] battleField, String[] buf) {
        if (inputCheck(buf, 2)) {
            System.out.println("Error! Wrong input of Destroyer! Try again:");
            return 4;
        } else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
            return 4;
        } else {
            updateBattleField(battleField, buf);
            printBattleField(10, battleField);
            return 5;
        }
    }

    private static int placeCruiser(char[][] battleField, String[] buf) {
        if (inputCheck(buf, 3)) {
            System.out.println("Error! Wrong input of Cruiser! Try again:");
            return 3;
        } else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
            return 3;
        } else {
            updateBattleField(battleField, buf);
            printBattleField(10, battleField);
            return 4;
        }
    }

    private static int placeSubmarine(char[][] battleField, String[] buf) {
        if (inputCheck(buf, 3)) {
            System.out.println("Error! Wrong input of Submarine! Try again:");
            return 2;
        } else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
            return 2;
        } else {
            updateBattleField(battleField, buf);
            printBattleField(10, battleField);
            return 3;
        }
    }

    private static int placeBattleship(char[][] battleField, String[] buf) {
        if (inputCheck(buf, 4)) {
            System.out.println("Error! Wrong input of Battleship! Try again:");
            return 1;
        } else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
            return 1;
        } else {
            updateBattleField(battleField, buf);
            printBattleField(10, battleField);
            return 2;
        }
    }

    private static int placeAircraftCarrier(char[][] battleField, String[] buf) {
        if (inputCheck(buf, 5)) {
            System.out.println("Error! Wrong input of Aircraft Carrier! Try again:");
            return 0;
        } else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
            return 0;
        } else {
            updateBattleField(battleField, buf);
            printBattleField(10, battleField);
            return 1;
        }
    }

    private static void updateBattleField(char[][] battleField, String[] buf) {
        int headX = Integer.parseInt(buf[0].substring(1)) - 1;
        int tailX = Integer.parseInt(buf[1].substring(1)) - 1;
        int headY = buf[0].charAt(0) - 'A';
        int tailY = buf[1].charAt(0) - 'A';
        if (headX > tailX) {
            int temp = headX;
            headX = tailX;
            tailX = temp;
        }
        if (headY > tailY) {
            int temp = headY;
            headY = tailY;
            tailY = temp;
        }
        if (headX == tailX) {
            // Vertical
            for (int i = headY; i <= tailY; i++) {
                battleField[i][headX] = 'O';
            }
        }
        else {
            // Horizontal
            for (int j = headX; j <= tailX; j++) {
                battleField[headY][j] = 'O';
            }
        }
    }

    private static boolean positionAdjustOrOccupied(char[][] battleField, String[] buf) {
        final int size = 10;
        int headX = Integer.parseInt(buf[0].substring(1)) - 1;
        int tailX = Integer.parseInt(buf[1].substring(1)) - 1;
        int headY = buf[0].charAt(0) - 'A';
        int tailY = buf[1].charAt(0) - 'A';
        if (headX > tailX) {
            int temp = headX;
            headX = tailX;
            tailX = temp;
        }
        if (headY > tailY) {
            int temp = headY;
            headY = tailY;
            tailY = temp;
        }
        boolean result = false;
        for (int i = headX - 1; i <= tailX + 1; i++) {
            if (i < 0 || i >= size) {
            }
            else {
                for (int j = headY - 1; j <= tailY + 1; j++) {
                    if (j < 0 || j >= size) {
                    }
                    else {
                        if (battleField[j][i] != '~') {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean inputCheck(String[] buf, int shipLength) {
        int headX, tailX;
        try {
            headX = Integer.parseInt(buf[0].substring(1));
            tailX = Integer.parseInt(buf[1].substring(1));
        }
        catch (NumberFormatException ex) {
            return true;
        }
        char headY = buf[0].charAt(0);
        char tailY = buf[1].charAt(0);
        if (buf.length != 2) {
            // Must be 2 coordinates
            return true;
        } else if (headY < 'A' || headY > 'J'
                || tailY < 'A' || tailY > 'J') {
            // Wrong Y
            return true;
        } else if (headX < 1 || headX > 10
                || tailX < 1 || tailX > 10) {
            // Wrong X
            return true;
        } else if (Math.abs(headX - tailX) + 1 != shipLength
                && Math.abs(headY - tailY) + 1 != shipLength) {
            // Wrong length of Ship
            return true;
        }
        else if (buf[0].compareTo(buf[1]) == 0) {
            // Two coordinates are the same
            return true;
        } else if (headY != tailY && headX != tailX) {
            // Neither horizontal nor vertical
            return true;
        } else {
            return false;
        }
    }

    private static void printBattleField(final int border, char[][] battleField) {
        // print column heading
        char[] rowHeading = "ABCDEFGHIJ".toCharArray();
        System.out.print(' ');
        for (int i = 0; i < border; i++) {
            System.out.print(' ');
            System.out.print(i+1);
        }
        System.out.println();
        // print each row
        for (int i = 0; i < border; i++) {
            System.out.print(rowHeading[i]);
            System.out.print(' ');
            for (int j = 0; j < border; j++) {
                System.out.print(battleField[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
