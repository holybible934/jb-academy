import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        LocalDate inputDate = LocalDate.of(Integer.parseInt(input[0]), Integer.parseInt(input[1]), 1);
        inputDate = inputDate.withDayOfMonth(inputDate.getMonth().length(inputDate.isLeapYear()));
        inputDate = inputDate.minusDays(Long.parseLong(input[2]) - 1);
        System.out.println(inputDate.toString());
    }
}