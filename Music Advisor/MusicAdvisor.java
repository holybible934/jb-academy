package advisor;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        String accessToken = null;
        Resources res = null;
        while (scanner.hasNext()) {
            cmd = scanner.next();
            if (cmd.equals("exit")) {
                System.out.println("---GOODBYE!---");
                return;
            } else if (accessToken == null) {
                if (cmd.equals("auth")) {
                    accessToken = doOAuth(args);
                    res = new Resources(args, accessToken);
                } else {
                    System.out.println("Please, provide access for application.");
                }
            } else {
                switch (cmd) {
                    case "new":
                        printNewRelease(res);
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

    private static String doOAuth(String[] args) throws IOException, InterruptedException {
        Authorization authorization = new Authorization(args);
        if (authorization.getAuthorization()) {
            return authorization.getAccessToken();
        } else {
            return null;
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

    private static void printNewRelease(Resources res) {
//        System.out.println("---NEW RELEASES---");
//        System.out.println("Mountains [Sia, Diplo, Labrinth]\n" +
//                "Runaway [Lil Peep]\n" +
//                "The Greatest Show [Panic! At The Disco]\n" +
//                "All Out Life [Slipknot]");
        String[] list = res.getNew();
    }
}
