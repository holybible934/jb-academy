package advisor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String cmd;
        String accessToken = null;
        Resources res = null;
        while (scanner.hasNext()) {
            cmd = scanner.nextLine();
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
            } else if (cmd.startsWith("playlists")) {
                StringBuilder playlist = new StringBuilder();
                String[] parsedCmd = cmd.split(" ");
                if (parsedCmd.length > 1) {
                    for (int i = 1; i < cmd.split(" ").length; i++) {
                        playlist.append(parsedCmd[i]).append(" ");
                    }
                    playlist.deleteCharAt(playlist.length() - 1);
                    printPlaylists(res, playlist);
                } else {
                    System.out.println("Not a correct command. Input again!");
                }
            } else {
                switch (cmd) {
                    case "new":
                        printNewRelease(res);
                        break;
                    case "featured":
                        printFeatured(res);
                        break;
                    case "categories":
                        printCategories(res);
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

    private static void printPlaylists(Resources res, StringBuilder playlist) {
        List<String> list = res.getPlaylists(playlist);
        if (list == null) {
            System.out.println("Unknown category name");
        } else {
            printList(list);
        }
    }

    private static void printCategories(Resources res) {
        List<String> list = res.getCategories();
        printList(list);
    }

    private static void printFeatured(Resources res) {
        List<String> list = res.getFeatured();
        printList(list);
    }

    private static void printNewRelease(Resources res) {
        List<String> list = res.getNew();
        printList(list);
    }

    private static void printList(List<String> list) {
        for (String str : list) {
            System.out.println(str);
            System.out.println();
        }
    }
}
