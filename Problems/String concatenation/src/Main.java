import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

class CurryConcat {

    public static String calc(String str1, String str2, String str3, String str4) {

        Function<String, Function<String, Function<String, Function<String, String>>>> stringFun = first -> second -> third -> fourth -> first.toLowerCase(Locale.ROOT) + third.toUpperCase(Locale.ROOT) + second.toLowerCase(Locale.ROOT) + fourth.toUpperCase(Locale.ROOT); // write your code here

        return stringFun.apply(str1).apply(str2).apply(str3).apply(str4);
    }

    // Don't change the code below
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] values = scanner.nextLine().split(" ");
        System.out.println(calc(values[0], values[1], values[2], values[3]));
    }
}