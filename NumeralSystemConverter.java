package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sourceBase;
        try {
            sourceBase = scanner.nextInt();
        }
        catch (Exception ex) {
            System.out.println("error source base");
            return;
        }
        scanner.nextLine();
        StringBuilder output = new StringBuilder();
        String buf = scanner.nextLine();
        if (sourceBase == 1) {
            int intPart = buf.length();
            int newBase = scanner.nextInt();
            output = getOutput(output, intPart, newBase);
        }
        else {
            if (buf.contains(".")) {
                String[] input = buf.split("\\.");
                try {
                    char[] inspector = input[0].toCharArray();
                    for (int i = 0; i < inspector.length; i++) {
                        Character.getNumericValue(inspector[i]);
                    }
                    inspector = input[1].toCharArray();
                    for (int i = 0; i < inspector.length; i++) {
                        Character.getNumericValue(inspector[i]);
                    }
                }
                catch (Exception ex) {
                    System.out.println("error! Not numeric to be converted");
                    return;
                }
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
                int intPart;
                int newBase;
                try {
                    intPart = Integer.parseInt(buf, sourceBase);
                    newBase = scanner.nextInt();
                }
                catch (Exception ex) {
                    System.out.println("error! wrong integer!");
                    return;
                }
                output = getOutput(output, intPart, newBase);
            }
        }
        System.out.println(output);
    }

    private static StringBuilder getOutput(StringBuilder output, int intPart, int newBase) {
        if (newBase > 1 && newBase < 37) {
            output = new StringBuilder((Integer.toString(intPart, newBase)));
        } else if (newBase == 1) {
            while (intPart-- > 0) {
                output.append(1);
            }
        }
        else {
            System.out.println("error! new base is less than 1 or greater than 37");
        }
        return output;
    }
}
