import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String word1 = scanner.nextLine().toLowerCase(Locale.ROOT);
        String word2 = scanner.nextLine().toLowerCase(Locale.ROOT);
        HashMap<Character, Integer> word1Map = new HashMap<>();
        for (char ch : word1.toCharArray()) {
            word1Map.put(ch, word1Map.getOrDefault(ch, 0) + 1);
        }
        HashMap<Character, Integer> word2Map = new HashMap<>();
        for (char ch : word2.toCharArray()) {
            word2Map.put(ch, word2Map.getOrDefault(ch, 0) + 1);
        }
        System.out.println(word1Map.equals(word2Map) ? "yes" : "no");
    }
}