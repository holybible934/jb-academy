import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    static void changeList(List<String> list) {
        // write your code here
        list.sort(new SortbyLength());
        String s = list.get(0);
        IntStream.range(1, list.size()).forEach(i -> list.set(i, s));
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> lst = Arrays.asList(s.split(" "));
        changeList(lst);
        lst.forEach(e -> System.out.print(e + " "));
    }
}

class SortbyLength implements Comparator<String>
{
    // Used for sorting in descending order by string length
    public int compare(String a, String b)
    {
        return b.length() - a.length();
    }
}