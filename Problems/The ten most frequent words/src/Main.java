import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);

        List<String> inputList = Arrays.asList(scanner.nextLine().toLowerCase().split("\\W+"));
        List<Word> wordsList = inputList.stream().distinct().map(Word::new).collect(Collectors.toList());
        for (Word w : wordsList) {
            w.setCount(inputList.stream()
                    .filter(str -> str.compareTo(w.getContent()) == 0)
                    .count());
        }
        List<Word> sortedTopTenList = wordsList.stream()
                .sorted((word, t1) -> {
                    if (word.getCount() != t1.getCount()) {
                        return (int) (t1.getCount() - word.getCount());
                    } else {
                        return word.getContent().compareTo(t1.getContent());
                    }
                })
                .limit(10)
                .collect(Collectors.toList());
        sortedTopTenList.stream().map(Word::getContent).forEach(System.out::println);
    }
}

class Word {

    private final String content;
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public Word(String word) {
        this.content = word;
        this.count = 1;
    }
}