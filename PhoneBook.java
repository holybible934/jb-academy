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
            counter = doJumpSearch(sortedNamesInDir, namesInFind);
            Instant jumpSearchEnd = Instant.now();
            printJumpSearch(bubbleSortStart, bubbleSortEnd, jumpSearchStart, jumpSearchEnd);
        }

        System.out.println("Start searching(quick sort + binary search)...");
        sortedNamesInDir = new ArrayList<>(namesInDir);
        Instant quickSortStart = Instant.now();
        doQuickSort(sortedNamesInDir);
        Instant quickSortEnd = Instant.now();
        Instant binarySearchStart = Instant.now();
        counter = doBinarySearch(sortedNamesInDir, namesInFind);
        Instant binarySearchEnd = Instant.now();
        printBinarySearch(quickSortStart, quickSortEnd, binarySearchStart, binarySearchEnd);
    }

    private static void printBinarySearch(Instant quickSortStart, Instant quickSortEnd, Instant binarySearchStart, Instant binarySearchEnd) {
        Duration total = Duration.between(quickSortStart, binarySearchEnd);
        long totalMin = total.toMinutesPart();
        long totalSec = total.toSecondsPart();
        long totalMs = total.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.%n", totalMin, totalSec, totalMs);
        Duration sort = Duration.between(quickSortStart,quickSortEnd);
        long sortMin = sort.toMinutesPart();
        long sortSec = sort.toSecondsPart();
        long sortMS = sort.toMillisPart();
        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n", sortMin, sortSec, sortMS);
        Duration search = Duration.between(binarySearchStart, binarySearchEnd);
        long searchMin = search.toMinutesPart();
        long searchSec = search.toSecondsPart();
        long searchMS = search.toMillisPart();
        System.out.printf("Searching time: %d min. %d sec. %d ms%n", searchMin, searchSec, searchMS);
    }

    private static int doBinarySearch(List<String> sortedNamesInDir, List<String> namesInFind) {
        int counter = 0;
        for (String str : namesInFind) {
            if (Collections.binarySearch(sortedNamesInDir, str) >= 0) {
                counter++;
            }
        }
        return counter;
    }

    private static void doQuickSort(List<String> sortedNamesInDir) {
        quickSort(sortedNamesInDir, 0, sortedNamesInDir.size() - 1);
    }

    private static void quickSort(List<String> sortedNamesInDir, int left, int right) {
        int pivot;
        if (right > left) {
            pivot = partition(sortedNamesInDir, left, right);
            quickSort(sortedNamesInDir, left, pivot - 1);
            quickSort(sortedNamesInDir, pivot + 1, right);
        }
    }

    private static int partition(List<String> sortedNamesInDir, int left, int right) {
        String P = sortedNamesInDir.get(left);
        int i = left;
        int j = right + 1;
        for (;;) {
            while (sortedNamesInDir.get(++i).compareTo(P) > 0)
                if (i >= right)
                    break;
            while (sortedNamesInDir.get(--j).compareTo(P) < 0)
                if (j <= left)
                    break;
            if (i >= j) {
                break;
            }
            else {
                Collections.swap(sortedNamesInDir, i, j);
            }
        }
        if (j == left) {
            return j;
        }
        Collections.swap(sortedNamesInDir, left, j);
        return j;
    }

    private static void printJumpSearch(Instant bubbleSortStart, Instant bubbleSortEnd, Instant jumpSearchStart, Instant jumpSearchEnd) {
        Duration total = Duration.between(bubbleSortStart, jumpSearchEnd);
        long totalMin = total.toMinutesPart();
        long totalSec = total.toSecondsPart();
        long totalMs = total.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.%n", totalMin, totalSec, totalMs);
        Duration sort = Duration.between(bubbleSortStart, bubbleSortEnd);
        long sortMin = sort.toMinutesPart();
        long sortSec = sort.toSecondsPart();
        long sortMS = sort.toMillisPart();
        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n", sortMin, sortSec, sortMS);
        Duration search = Duration.between(jumpSearchStart, jumpSearchEnd);
        long searchMin = search.toMinutesPart();
        long searchSec = search.toSecondsPart();
        long searchMS = search.toMillisPart();
        System.out.printf("Searching time: %d min. %d sec. %d ms%n", searchMin, searchSec, searchMS);
    }

    private static int doJumpSearch(List<String> sortedNamesInDir, List<String> namesInFind) {
        int jumpLength = (int) Math.sqrt(sortedNamesInDir.size());
        int counter = 0;
        int blockLeft;
        int blockRight = 0;
        for (String target : namesInFind) {
            while (blockRight < sortedNamesInDir.size() - 1) {
                blockLeft = blockRight;
                blockRight = Math.min(sortedNamesInDir.size() - 1, blockRight + jumpLength);
                if (sortedNamesInDir.get(blockRight).compareTo(target) >= 0) {
                    for (int i = blockRight; i >= blockLeft; i--) {
                        if (sortedNamesInDir.get(i).contains(target)) {
                            System.out.printf("Block Left is %d, Block Right is %d%n", blockLeft, blockRight);
                            counter++;
                        }
                    }
                }

            }
        }
        return counter;
    }

    private static void printLinearSearch(Instant bubbleSortStart, Instant bubbleSortEnd, Instant linearSearchStart, Instant linearSearchEnd) {
        Duration total = Duration.between(bubbleSortStart, linearSearchEnd);
        long totalMin = total.toMinutesPart();
        long totalSec = total.toSecondsPart();
        long totalMs = total.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.%n", totalMin, totalSec, totalMs);
        Duration sort = Duration.between(bubbleSortStart, bubbleSortEnd);
        long sortMin = sort.toMinutesPart();
        long sortSec = sort.toSecondsPart();
        long sortMS = sort.toMillisPart();
        System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, move to linear search%n", sortMin, sortSec, sortMS);
        Duration search = Duration.between(linearSearchStart, linearSearchEnd);
        long searchMin = search.toMillisPart();
        long searchSec = search.toSecondsPart();
        long searchMS = search.toMillisPart();
        System.out.printf("Searching time: %d min. %d sec. %d ms%n", searchMin, searchSec, searchMS);
    }

    private static void printLinearSearch(Instant linearSearchStart, Instant linearSearchEnd) {
        Duration d = Duration.between(linearSearchStart, linearSearchEnd);
        long minutes = d.toMinutesPart();
        long seconds = d.toSecondsPart();
        long millis = d.toMillisPart();
        System.out.printf("Found 500 / 500 entries. Time taken: %d min. %d sec. %d ms.%n", minutes, seconds, millis);
    }

    private static int doLinearSearch(List<String> namesInFind, List<String> namesInDir) {
        int counter = 0;
        for (String str : namesInFind) {
            for (String dir : namesInDir) {
                if (dir.contains(str)) {
                    counter++;
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
