package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File find = new File("/Users/chchang/Downloads/find.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(find);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        List<String> namesInFind = new ArrayList<>();
        while (scanner.hasNextLine()) {
            namesInFind.add(scanner.nextLine());
        }
        System.out.println("Start searching...");
        long beforeSearch = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            for (String str : namesInFind) {
                //System.out.println(str);
                StringBuilder sb = new StringBuilder(str);
            }
        }
        long afterSearch = System.currentTimeMillis();
        long minute = (afterSearch - beforeSearch) / 1000 / 60;
        long second = (afterSearch - beforeSearch) / 1000 % 60;
        long millisecond = (afterSearch - beforeSearch) % 1000;
        //System.out.println("Found 500 / 500 entries. Time taken: 1 min. 56 sec. 328 ms.");
        System.out.printf("Found 500 / 500 entries. Time take: %d min. %d sec. %d ms.%n", minute, second, millisecond);
    }
}
