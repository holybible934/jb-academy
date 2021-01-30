package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File find = new File("/Users/chchang/Downloads/find.txt");
        File directory = new File("/Users/chchang/Downloads/directory.txt");
        Scanner findScanner;
        Scanner directoryScanner;
        try {
            findScanner = new Scanner(find);
            directoryScanner = new Scanner(directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        List<String> namesInFind = new ArrayList<>();
        List<String> namesInDir = new ArrayList<>();
        while (findScanner.hasNextLine()) {
            namesInFind.add(findScanner.nextLine());
        }
        while (directoryScanner.hasNextLine()) {
            namesInDir.add(directoryScanner.nextLine());
        }
        System.out.println("Start linear searching...");
        Instant linearSearchStart = Instant.now();
        int counter = doLinearSearch(namesInFind, namesInDir);
        Instant linearSearchEnd = Instant.now();
        printLinearSearch(linearSearchStart, linearSearchEnd);

        System.out.println("Start searching(bubble sort + jump search)...");
        List<String> sortedNamesInDir = new ArrayList<>(namesInDir);
        Instant bubbleSortStart = Instant.now();
        bubbleSort(sortedNamesInDir);
        Instant bubbleSortEnd = Instant.now();
        if ((Duration.between(linearSearchStart, linearSearchEnd).compareTo(Duration.between(bubbleSortStart, bubbleSortEnd)
                .dividedBy(10)) < 0)) {
            linearSearchStart = Instant.now();
            counter = doLinearSearch(namesInFind, namesInDir);
            linearSearchEnd = Instant.now();
            printLinearSearch(bubbleSortStart, bubbleSortStart, linearSearchStart, linearSearchEnd);
        }
        else {
            Instant jumpSearchStart = Instant.now();
            //counter = doJumpSearch(sortedNamesInDir, namesInFind);
            Instant jumpSearchEnd = Instant.now();
            //printJumpSearch(bubbleSortStart, bubbleSortEnd, jumpSearchStart, jumpSearchEnd);
        }
    }

    private static void printLinearSearch(Instant bubbleSortStart, Instant bubbleSortEnd, Instant linearSearchStart, Instant linearSearchEnd) {
        Duration total = Duration.between(bubbleSortStart, linearSearchEnd);
        long totalMin = total.toMinutes();
        long totalSec = total.toSeconds();
        long totalMs = total.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.", totalMin, totalSec, totalMs);
        Duration sort = Duration.between(bubbleSortStart,bubbleSortEnd);
        long sortMin = sort.toMinutes();
        long sortSec = sort.toSeconds();
        long sortMS = sort.toMillisPart();
        System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, move to linear search", sortMin, sortSec, sortMS);
        Duration search = Duration.between(linearSearchStart, linearSearchEnd);
        long searchMin = search.toMinutes();
        long searchSec = search.toSeconds();
        long searchMS = search.toMillisPart();
        System.out.printf("Searching time: %d min. %d sec. %d ms", searchMin, searchSec, searchMS);
    }

    private static void printLinearSearch(Instant linearSearchStart, Instant linearSearchEnd) {
        Duration d = Duration.between(linearSearchStart, linearSearchEnd);
        long minutes = d.toMinutes();
        long seconds = d.toSeconds();
        long millis = d.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.", minutes, seconds, millis);
    }

    private static int doLinearSearch(List<String> namesInFind, List<String> namesInDir) {
        int counter = 0;
        for (String str : namesInFind) {
            for (String dir : namesInDir) {
                if (dir.contains(str)) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    private static void bubbleSort(List<String> namesInFind) {
        for (int i = 0; i < namesInFind.size() - 1; i++) {
            if (namesInFind.get(i).compareTo(namesInFind.get(i + 1)) < 0) {
                String temp = namesInFind.get(i + 1);
                namesInFind.remove(i + 1);
                namesInFind.add(i, temp);
            }
        }
    }
}
