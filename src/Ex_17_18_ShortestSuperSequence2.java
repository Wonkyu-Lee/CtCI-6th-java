public class Ex_17_18_ShortestSuperSequence2 {
    class Range {
        final int start;
        final int end;
        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
        int length() {
            return end - start + 1;
        }
        boolean shorterThan(Range other) {
            return length() < other.length();
        }
    }

    Range solve(int[] small, int[] big) {
        int[] closures = new int[big.length];
        for (int i = 0; i < small.length; ++i) {
            for (int j = big.length - 1; j >= 0; --j) {

            }
        }

        return null;
    }
}
