import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] logLevels = scanner.nextLine().toUpperCase().split(" ");
        int levelSum = Arrays.stream(logLevels).mapToInt(s -> Level.parse(s).intValue()).sum();
        System.out.println(levelSum);
    }
}