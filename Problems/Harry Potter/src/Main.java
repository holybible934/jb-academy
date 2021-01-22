import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String house = scanner.nextLine();
        String[] output = {"not a valid house", "bravery", "loyalty", "cunning", "intellect"};
        int houseIndex = 0;
        switch (house) {
            case "gryffindor":
                houseIndex = 1;
                break;
            case "hufflepuff":
                houseIndex = 2;
                break;
            case "slytherin":
                houseIndex = 3;
                break;
            case "ravenclaw":
                houseIndex = 4;
            default:
        }
        System.out.println(output[houseIndex]);
    }
}