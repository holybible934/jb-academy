import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String inputTime = scanner.nextLine();
        LocalDateTime datetime = LocalDateTime.parse(inputTime);
        datetime = datetime.plusMinutes(scanner.nextLong());
        System.out.println(datetime.getYear() +
                " " + datetime.getDayOfYear() +
                " " + datetime.toLocalTime());
    }
}