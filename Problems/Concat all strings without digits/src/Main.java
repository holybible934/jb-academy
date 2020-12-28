import java.util.Scanner;

class ConcatenateStringsProblem {

    public static String concatenateStringsWithoutDigits(String[] strings) {
        // write your code with StringBuilder here
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            StringBuilder temp = new StringBuilder(str);
            for (int i = 0; i < temp.length(); i++) {
                if (Character.isDigit(temp.charAt(i))) {
                    temp.deleteCharAt(i);
                    i--;
                }
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] strings = scanner.nextLine().split("\\s+");
        String result = concatenateStringsWithoutDigits(strings);
        System.out.println(result);
    }
}