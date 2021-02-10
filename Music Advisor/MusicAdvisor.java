package advisor;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        String accessToken = null;
        while (scanner.hasNext()) {
            cmd = scanner.next();
            if (cmd.equals("exit")) {
                System.out.println("---GOODBYE!---");
                return;
            } else if (accessToken == null) {
                if (cmd.equals("auth")) {
                    accessToken = doOAuth();
                    System.out.println("---SUCCESS---");
                } else {
                    System.out.println("Please, provide access for application.");
                }
            } else {
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
                    default:
                        System.out.println("Not a correct command. Input again!");
                }
            }
        }
    }

    private static String doOAuth() {
        String spotifyAuthUrl = "https://accounts.spotify.com/authorize?client_id=0e90298f01c545f3a4d7c47f15ce0a13&redirect_uri=http://localhost:8080&response_type=code";
        System.out.println(spotifyAuthUrl);
        return "VALID_ACCESS_TOKEN";
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
