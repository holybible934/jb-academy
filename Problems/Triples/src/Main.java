import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        final int arrSize = scanner.nextInt();
        int[] arr = new int[arrSize];
        for (int i = 0; i < arrSize; i++) {
            arr[i] = scanner.nextInt();
        }
        int triples = 0;
        for (int i = 0; i < arrSize - 2; i++) {
            if (arr[i + 1] == arr[i] + 1 && arr[i + 2] == arr[i + 1] + 1) {
                triples++;
            }
        }
        System.out.println(triples);
    }
}