package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        Scanner scanner = new Scanner(System.in);
        String ticket = scanner.nextLine();
        int sumOfFirst = ticket.substring(0, ticket.length() / 2).chars().sum();
        int sumOfLast = ticket.substring(ticket.length() / 2).chars().sum();
        System.out.println(sumOfFirst == sumOfLast ? "Lucky" : "Regular");
    }
}
