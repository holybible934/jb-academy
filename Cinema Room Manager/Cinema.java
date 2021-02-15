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
                case 3:
                    printStatistics(cinemaRoom);
                    break;
                case 0:
                    return;
            }
            printMenu();
        }
    }

    private static void printStatistics(char[][] cinemaRoom) {
        int totalIncome = checkTotalIncome(cinemaRoom);
        int currentIncome = checkCurrentIncome(cinemaRoom);
        int purchasedTickets = countBookedTickets(cinemaRoom);
        double percentage = ((double) purchasedTickets / (double) ((cinemaRoom.length - 1) * (cinemaRoom[0].length - 1))) * 100;
        System.out.println("\n" + "Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%c%n", percentage, '%');
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }

    private static int checkCurrentIncome(char[][] cinemaRoom) {
        int currentIncome = 0;
        for (int i = 1; i < cinemaRoom.length; i++) {
            for (int j = 1; j < cinemaRoom[i].length; j++) {
                if (cinemaRoom[i][j] == 'B') {
                    currentIncome += checkTicketPrice(cinemaRoom.length - 1, cinemaRoom[0].length - 1, i);
                }
            }
        }
        return currentIncome;
    }

    private static int countBookedTickets(char[][] cinemaRoom) {
        int bookedSeats = 0;
        for (char[] rows : cinemaRoom) {
            for (char seat : rows) {
                if (seat == 'B') {
                    bookedSeats++;
                }
            }
        }
        return bookedSeats;
    }

    private static int checkTotalIncome(char[][] cinemaRoom) {
        int totalIncome = 0;
        for (int i = 1; i < cinemaRoom.length; i++) {
            for (int j = 1; j < cinemaRoom[i].length; j++) {
                totalIncome += checkTicketPrice(cinemaRoom.length - 1, cinemaRoom[0].length - 1, i);
            }
        }
        return totalIncome;
    }

    private static void buyATicket(Scanner scanner, char[][] cinemaRoom) {
        boolean purchaseDone = false;
        while (!purchaseDone) {
            System.out.println("Enter a row number:");
            int seatRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatColumn = scanner.nextInt();
            if (seatRow >= cinemaRoom.length || seatColumn >= cinemaRoom[0].length) {
                System.out.println("Wrong input");
                continue;
            }
            if (cinemaRoom[seatRow][seatColumn] != 'B') {
                cinemaRoom[seatRow][seatColumn] = 'B';
                int ticketPrice = checkTicketPrice(cinemaRoom.length - 1, cinemaRoom[0].length - 1, seatRow);
                System.out.println("Ticket price: $" + ticketPrice);
                purchaseDone = true;
            } else {
                System.out.println("That ticket has already been purchased");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
    }

    private static int checkTicketPrice(int rows, int seatsPerRow, int seatRow) {
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

    private static void initRowHeader(char[][] cinemaRoom) {
        for (int i = 0; i < cinemaRoom[0].length; i++) {
            if (i == 0) {
                cinemaRoom[0][i] = ' ';
            } else {
                cinemaRoom[0][i] = (char) (i + '0');
            }
        }
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