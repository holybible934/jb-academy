package advisor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int PAGE_SIZE = 5;
    private static int currentPageIndex = 0;
    private static int totalPages = 0;
    private static List<String> currentList;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String cmd;
        String accessToken = null;
        Resources res = null;
        if (Arrays.asList(args).contains("-page")) {
            PAGE_SIZE = Integer.parseInt(args[5]);
        }
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
            } else if (cmd.equals("prev")) {
                if (currentList.size() == 0 || currentPageIndex == 0) {
                    System.out.println("No more pages.");
                } else {
                    currentPageIndex--;
                    printList(currentList);
                }
            } else if (cmd.equals("next")) {
                if (currentList.size() == 0 || currentPageIndex + 1 == totalPages) {
                    System.out.println("No more pages");
                } else {
                    currentPageIndex++;
                    printList(currentList);
                }
            } else {
                currentPageIndex = 0;
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
        currentList = list;
        totalPages = list.size() % PAGE_SIZE == 0? list.size() / PAGE_SIZE : list.size() / PAGE_SIZE + 1;
        for (int i = currentPageIndex * PAGE_SIZE; i < Math.min((currentPageIndex + 1) * PAGE_SIZE, list.size()); i++) {
            System.out.println(list.get(i));
        }
        System.out.printf("---PAGE %d OF %d---%n", currentPageIndex + 1, totalPages);
    }
}
