package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sourceBase = scanner.nextInt();
        scanner.nextLine();
        StringBuilder output = new StringBuilder();
        String buf = scanner.nextLine();
        if (buf.contains(".")) {
            String[] input = buf.split("\\.");
            int intPart = Integer.parseInt(input[0], sourceBase);
            char[] decimals = input[1].toCharArray();
            double fracPart = 0.0;
            for (int i = 0; i < decimals.length; i++) {
                int digit = Character.getNumericValue(decimals[i]);
                fracPart += digit / Math.pow(sourceBase, i + 1);
            }
            int newBase = scanner.nextInt();
            output = new StringBuilder(Integer.toString(intPart, newBase) + '.');
            for (int i = 0; i < 5; i++) {
                String temp = String.valueOf(fracPart * newBase);
                int beforeDecimals = (int) Double.parseDouble(temp);
                output.append(Character.forDigit(beforeDecimals, newBase));
                int indexOfDecimal = temp.indexOf(".");
                fracPart = Double.parseDouble("0" + temp.substring(indexOfDecimal));
            }
        } else {
            int intPart = Integer.parseInt(buf, sourceBase);
            int newBase = scanner.nextInt();
            if (newBase > 1) {
                output = new StringBuilder((Integer.toString(intPart, newBase)));
            }
            else {
                while(intPart-- > 0) {
                    output.append(1);
                }
            }
        }
        System.out.println(output);
    }
}
