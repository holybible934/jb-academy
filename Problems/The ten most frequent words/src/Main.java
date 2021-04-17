import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Stream.of(new Scanner(System.in).nextLine().split("\\W+"))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> {
                    final long valueDiff = o2.getValue() - o1.getValue();
                    return valueDiff == 0 ? o1.getKey().compareTo(o2.getKey()) : (int) valueDiff;
                })
                .limit(10)
                .forEach(e -> System.out.println(e.getKey()));
    }
}