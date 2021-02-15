package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int seatsPerRow = scanner.nextInt();
        printSeats(rows, seatsPerRow);
        System.out.println("Enter a row number:");
        final int seatRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        final int seatColumn = scanner.nextInt();
        int ticketPrice = checkTicketPrice(rows, seatsPerRow, seatRow, seatColumn);
        System.out.println("Ticket price: $" + ticketPrice);
        printBookedSeat(rows, seatsPerRow, seatRow, seatColumn);
    }

    private static void printBookedSeat(int rows, int seatsPerRow, int seatRow, int seatColumn) {
        System.out.println("Cinema:");
        printRowHeader(seatsPerRow);
        System.out.println(seatsPerRow);
        for (int i = 1; i <= rows; i++) {
            System.out.print(i + " ");
            if (i != seatRow) {
                for (int j = 1; j < seatsPerRow; j++) {
                    System.out.print("S ");
                }
                System.out.println("S");
            } else {
                if (seatColumn != seatsPerRow) {
                    for (int j = 1; j < seatsPerRow; j++) {
                        if (j == seatColumn) {
                            System.out.print("B ");
                        } else {
                            System.out.print("S ");
                        }
                    }
                    System.out.println("S");
                } else {
                    for (int j = 1; j < seatsPerRow; j++) {
                        System.out.print("S ");
                    }
                    System.out.println("B");
                }
            }
        }
    }

    private static void printRowHeader(int seatsPerRow) {
        for (int i = 0; i < seatsPerRow; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
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

    private static void printSeats(int rows, int seatsPerRow) {
        System.out.println("Cinema:");
        printRowHeader(seatsPerRow);
        System.out.println(seatsPerRow);
        for (int i = 1; i <= rows; i++) {
            System.out.print(i + " ");
            for (int j = 1; j < seatsPerRow; j++) {
                System.out.print("S ");
            }
            System.out.println("S");
        }
    }
}