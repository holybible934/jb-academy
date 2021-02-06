package advisor;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        while (scanner.hasNext()) {
            cmd = scanner.next();
            switch (cmd) {
                case "new":
                    printNewRelease();
                    break;
                case "featured":
                    printFeatured();
                    break;
                case "categories":
                    printCategories();
                    break;
                case "playlists":
                    printPlaylists(scanner);
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
                default:
                    System.out.println("Not a correct command. Input again!");
            }
        }
    }

    private static void printPlaylists(Scanner scanner) {
        String type = scanner.next().toUpperCase(Locale.ROOT);
        System.out.println("---" + type + " PLAYLISTS---");
        System.out.println("Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    private static void printCategories() {
        System.out.println("---CATEGORIES---");
        System.out.println("Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    private static void printFeatured() {
        System.out.println("---FEATURED---");
        System.out.println("Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    private static void printNewRelease() {
        System.out.println("---NEW RELEASES---");
        System.out.println("Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    }
}
