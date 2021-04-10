import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int dictionarySize = Integer.parseInt(scanner.nextLine());
        HashSet<String> dictionary = new HashSet<>();
        for (int i = 0; i < dictionarySize; i++) {
            dictionary.add(scanner.nextLine().toLowerCase());
        }
        final int inputSize = Integer.parseInt(scanner.nextLine());
        HashSet<String> inputSet = new HashSet<>();
        for (int i = 0; i < inputSize; i++) {
            inputSet.addAll(Arrays.stream(scanner.nextLine().toLowerCase().split("\\s+"))
                    .collect(Collectors.toCollection(HashSet::new)));
        }
        inputSet.stream().filter(s -> !dictionary.contains(s)).forEach(System.out::println);
    }
}