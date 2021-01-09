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
                    allocPhase = 2;
                    if (allocPhase == 2) {
                        System.out.println("Enter the coordinates of the Submarine (3 cells):");
                    }
                    break;
                case 2:
                    allocPhase = 3;
                    if (allocPhase == 3) {
                        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
                    }
                    break;
                case 3:
                    allocPhase = 4;
                    if (allocPhase == 4) {
                        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
                    }
                    break;
                case 4:
                    allocPhase = 5;
                    break;
                default:
            }
            buf = scanner.nextLine().toUpperCase(Locale.ROOT).split(" ");
        }
    }

    private static int placeAircraftCarrier(char[][] battleField, String[] buf) {
        if (!inputCheck(buf, 5)) {
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
        final int size = 10;
        int headX = Integer.parseInt(buf[0].substring(1));
        int tailX = Integer.parseInt(buf[1].substring(1));
        int headY = buf[0].charAt(0) - 'A';
        int tailY = buf[1].charAt(0) - 'A';
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
        boolean result = false;
        for (int i = headX - 1; i <= headX + 1; i++) {
            if (i < 0 || i > size) {
            }
            else {
                for (int j = headY - 1; j <= tailY + 1; j++) {
                    if (j < 0 || j > size) {
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
            return false;
        }
        char headY = buf[0].charAt(0);
        char tailY = buf[1].charAt(0);
        if (buf.length != 2) {
            // Must be 2 coordinates
            return false;
        } else if (headY < 'A' || headY > 'J'
                || tailY < 'A' || tailY > 'J') {
            // Wrong Y
            return false;
        } else if (headX < 1 || headX > 10
                || tailX < 1 || tailX > 10) {
            // Wrong X
            return false;
        } else if (Math.abs(headX - tailX) + 1 != shipLength
                && Math.abs(headY - tailY) + 1 != shipLength) {
            // Wrong length of Ship
            return false;
        }
        else if (buf[0].compareTo(buf[1]) == 0) {
            // Two coordinates are the same
            return false;
        } else if (headY != tailY && headX != tailX) {
            // Neither horizontal nor vertical
            return false;
        } else {
            return true;
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
