package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int rows = scanner.nextInt() + 1;
        System.out.println("Enter the number of seats in each row:");
        final int seatsPerRow = scanner.nextInt() + 1;
        char[][] cinemaRoom = setupCinema(rows, seatsPerRow);
        printMenu();
        while (scanner.hasNext()) {
            int cmd = scanner.nextInt();
            switch (cmd) {
                case 1:
                    printCinemaRoom(cinemaRoom);
                    break;
                case 2:
                    buyATicket(scanner, cinemaRoom);
                    break;
                case 0:
                    return;
            }
            printMenu();
        }
    }

    private static void buyATicket(Scanner scanner, char[][] cinemaRoom) {
        System.out.println("Enter a row number:");
        final int seatRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        final int seatColumn = scanner.nextInt();
        cinemaRoom[seatRow][seatColumn] = 'B';
        int ticketPrice = checkTicketPrice(cinemaRoom.length - 1, cinemaRoom[0].length - 1, seatRow, seatColumn);
        System.out.println("Ticket price: $" + ticketPrice);
    }

    private static void printMenu() {
        System.out.println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "0. Exit");
    }

    private static void initRowHeader(char[][] cinemaRoom) {
        for (int i = 0; i < cinemaRoom[0].length; i++) {
            if (i == 0) {
                cinemaRoom[0][i] = ' ';
            } else {
                cinemaRoom[0][i] = (char) (i + '0');
            }
        }
    }

    private static int checkTicketPrice(int rows, int seatsPerRow, int seatRow, int seatColumn) {
        if (rows * seatsPerRow > 60) {
            if (seatRow <= rows / 2) {
                return 10;
            } else {
                return 8;
            }
        } else {
            return 10;
        }
    }

    private static char[][] setupCinema(int rows, int seatsPerRow) {
        char[][] cinemaRoom = new char[rows][seatsPerRow];
        initRowHeader(cinemaRoom);
        for (int i = 1; i < rows; i++) {
            cinemaRoom[i][0] = (char) (i + '0');
            for (int j = 1; j < seatsPerRow; j++) {
                cinemaRoom[i][j] = 'S';
            }
        }
        printCinemaRoom(cinemaRoom);
        return cinemaRoom;
    }

    private static void printCinemaRoom(char[][] cinemaRoom) {
        System.out.println("Cinema:");
        for (char[] rows : cinemaRoom) {
            for (char seat : rows) {
                System.out.print(seat);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}