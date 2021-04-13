import java.util.stream.*;

class QuadraticSum {
    public static long rangeQuadraticSum(int fromIncl, int toExcl) {
        return IntStream.range(fromIncl, toExcl) // write your code with streams here
                .reduce(0, (a, b) -> a + b * b);
    }
}