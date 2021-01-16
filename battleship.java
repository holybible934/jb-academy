package battleship;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialize battle field
        final int border = 10;
        char[][] player1BattleField = new char[border][border];
        for (int i = 0; i < border; i++) {
            for (int j = 0; j < border; j++) {
                player1BattleField[i][j] = '~';
            }
        }
        char[][] player2BattleField = Arrays.stream(player1BattleField).map(char[]::clone).toArray(char[][]::new);
        Ship[] player1Ships = new Ship[5];
        Ship[] player2Ships = new Ship[5];

        // Player 1 take positions
        printBattleField(border, player1BattleField);
        System.out.println("Player 1, place your ships on the game field");
        Scanner scanner = new Scanner(System.in);
        String[] buf;
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        for (int i = 0; i < 5; i++) {
            buf = scanner.nextLine().toUpperCase(Locale.ROOT).split(" ");
            player1Ships[i] = placeShip(i, player1BattleField, buf);
            if (player1Ships[i] == null) {
                i--;
            }
        }

        //Player 2 take positions
        playerRotate(scanner);
        printBattleField(border, player2BattleField);
        System.out.println("Player 2, place your ships to the game field");
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        for (int i = 0; i < 5; i++) {
            buf = scanner.nextLine().toUpperCase(Locale.ROOT).split(" ");
            player2Ships[i] = placeShip(i, player2BattleField, buf);
            if (player2Ships[i] == null) {
                i--;
            }
        }
        playerRotate(scanner);

        // Game play
        boolean player1Turn = true;
        boolean gameNotFinished = true;
        String target = "";
        while (gameNotFinished) {
            if (player1Turn) {
                printBattleFieldWithFog(border, player2BattleField);
                System.out.println("---------------------");
                printBattleField(border, player1BattleField);
                System.out.println("Player 1, it's your turn:");
                target = scanner.nextLine();
                gameNotFinished = takeAShot(target, player2BattleField, player2Ships);
                player1Turn = false;
            }
            else {
                printBattleFieldWithFog(border, player1BattleField);
                System.out.println("---------------------");
                printBattleField(border, player2BattleField);
                System.out.println("Player 2, it's your turn:");
                target = scanner.nextLine();
                gameNotFinished = takeAShot(target, player1BattleField, player1Ships);
                player1Turn = true;
            }
            playerRotate(scanner);
        }
    }

    private static void playerRotate(Scanner scanner) {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static Ship placeShip(int i, char[][] battleField, String[] buf) {
        int[] shipSizes = {5, 4, 3, 3, 2};
        String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        Ship ship = null;
        if (inputCheck(buf, shipSizes[i])) {
            System.out.println("Error! Wrong input of " + shipNames[i] + "! Try again:");
        }
        else if (positionAdjustOrOccupied(battleField, buf)) {
            System.out.println("Error! You placed it wrongly or too close to another one. Try again:");
        }
        else {
            ship = updateBattleField(battleField, buf, shipNames[i]);
            printBattleField(battleField.length, battleField);
            if (i + 1 < 5) {
                System.out.println("Enter the coordinates of the " + shipNames[i+1] + "(" + shipSizes[i+1] + " cells):");
            }
        }
        return ship;
    }

    static class Ship {
        int headX;
        int tailX;
        int headY;
        int tailY;
        String shipName;

        public Ship(int headX, int tailX, int headY, int tailY, String shipName) {
            if (headX <= tailX) {
                this.headX = headX;
                this.tailX = tailX;
            }
            else {
                this.headX = tailX;
                this.tailX = headX;
            }
            if (headY <= tailY) {
                this.headY = headY;
                this.tailY = tailY;
            }
            else {
                this.headY = tailY;
                this.tailY = headY;
            }
            this.shipName = shipName;
        }

        public boolean isHit(int hitX, int hitY) {
            return (hitX >= this.headX && hitX <= this.tailX) && (hitY >= this.headY && hitY <= this.tailY);
        }

        public boolean isVertical() {
            return this.headY != this.tailY;
        }

        public boolean isSunk(char[][] battleField) {
            for (int i = this.headY; i <= this.tailY; i++) {
                for (int j = this.headX; j <= this.tailX; j++) {
                    if (battleField[i][j] == 'O') {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static void printBattleFieldWithFog(int border, char[][] battleField) {
        char[][] fogField = Arrays.stream(battleField).map(char[]::clone).toArray(char[][]::new);
        for (int i = 0; i < border; i++) {
            for (int j = 0; j < border; j++) {
                if (fogField[i][j] != 'X' && fogField[i][j] != 'M') {
                    fogField[i][j] = '~';
                }
            }
        }
        printBattleField(border, fogField);
    }

    private static boolean takeAShot(String target, char[][] battleField, Ship[] myShips) {
        int targetX = Integer.parseInt(target.substring(1)) - 1;
        int targetY = target.charAt(0) - 'A';
        final int border = battleField.length - 1;
        if (targetX < 0 || targetX > border || targetY < 0 || targetY > border) {
          System.out.println("Error! You entered the wrong coordinates! Try again:");
          return true;
        } else if (battleField[targetY][targetX] == '~') {
            battleField[targetY][targetX] = 'M';
            printBattleFieldWithFog(battleField.length, battleField);
            System.out.println("You missed! Try again:");
            return true;
        } else {
            battleField[targetY][targetX] = 'X';
            for (Ship ship : myShips) {
                if (ship.isHit(targetX, targetY)) {
                    if (ship.isSunk(battleField)) {
                        System.out.println("You sank a ship! Specify a new target:");
                    } else {
                        System.out.println("You hit a ship!");
                    }
                    break;
                }
            }
            printBattleFieldWithFog(battleField.length, battleField);
            // Check all ships are sunk or not
            for (Ship myShip : myShips) {
                if (!myShip.isSunk(battleField)) {
                    return true;
                }
            }
            System.out.println("You sank the last ship. You won. Congratulations!");
            return false;
        }
    }

    private static Ship updateBattleField(char[][] battleField, String[] buf, String shipName) {
        int headX = Integer.parseInt(buf[0].substring(1)) - 1;
        int tailX = Integer.parseInt(buf[1].substring(1)) - 1;
        int headY = buf[0].charAt(0) - 'A';
        int tailY = buf[1].charAt(0) - 'A';
        Ship ship = new Ship(headX, tailX, headY, tailY, shipName);
        if (ship.isVertical()) {
            for (int i = ship.headY; i <= ship.tailY; i++) {
                battleField[i][ship.headX] = 'O';
            }
        }
        else {
            for (int j = ship.headX; j <= ship.tailX; j++) {
                battleField[ship.headY][j] = 'O';
            }
        }
        return ship;
    }

    private static boolean positionAdjustOrOccupied(char[][] battleField, String[] buf) {
        final int size = battleField.length;
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
        for (int i = headX - 1; i <= tailX + 1; i++) {
            if (i >= 0 && i < size) {
                for (int j = headY - 1; j <= tailY + 1; j++) {
                    if (j >= 0 && j < size) {
                        if (battleField[j][i] != '~') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        else // Neither horizontal nor vertical
            if (buf[0].compareTo(buf[1]) == 0) {
            // Two coordinates are the same
            return true;
        } else return headY != tailY && headX != tailX;
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
