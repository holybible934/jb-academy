package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        String ticketNum = scanner.nextLine();
        final int ticketLength = 6;
        int sumOfFirst = 0;
        int sumOfLast = 0;
        for (int i = 0; i < ticketLength / 2; i++) {
            sumOfFirst += Integer.parseInt(ticketNum.substring(i, i + 1));
            sumOfLast += Integer.parseInt(ticketNum.substring(i + ticketLength / 2, i + ticketLength / 2 + 1));
        }
        System.out.println(sumOfFirst == sumOfLast ? "Lucky" : "Regular");
    }
}
