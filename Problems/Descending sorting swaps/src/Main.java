import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        List<String> inputStr = Arrays.asList(scanner.nextLine().split(" "));
        if (!inputStr.isEmpty()) {
            List<Integer> inputInt = inputStr.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
            for (int i = 0; i < inputInt.size(); i++) {
                for (int j = 0; j < inputInt.size() - 1; j++) {
                    if (inputInt.get(j) < inputInt.get(j + 1)) {
                        int temp = inputInt.get(j);
                        inputInt.set(j, inputInt.get(j + 1));
                        inputInt.set(j + 1, temp);
                        counter++;
                    }
                }
            }
        }
        System.out.println(counter);
    }
}