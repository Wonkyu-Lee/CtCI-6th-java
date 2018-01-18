import java.util.HashSet;
import java.util.Set;

public class Ex_17_18_ShortestSuperSequence {

    class MatchChecker {
        final Set<Integer> keys;
        final Set<Integer> checker = new HashSet<>();

        MatchChecker(Set<Integer> keys) {
            this.keys = keys;
        }

        void check(int key) {
            if (keys.contains(key)) {
                checker.add(key);
            }
        }

        boolean isCompleted() {
            return checker.size() == keys.size();
        }
    }

    Set<Integer> createKeys(int[] small) {
        Set<Integer> keys = new HashSet<>();
        for (int i = 0; i < small.length; ++i) {
            keys.add(small[i]);
        }

        return keys;
    }

    class Range {
        final int start;
        final int end;
        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    Range solve(int[] small, int[] big) {
        Set<Integer> keys = createKeys(small);

        MatchChecker[] dp = new MatchChecker[big.length + 1];
        for (int i = 0; i <= big.length; ++i) {
            dp[i] = new MatchChecker(keys);
        }

        for (int size = 1; size <= big.length; ++size) {
            for (int i = 0; i <= big.length - size; ++i) {
                if (isCompleted(big, i, size, dp)) {
                    return new Range(i, i + size - 1);
                }
            }
        }

        return null;
    }

    boolean isCompleted(int[] big, int start, int size, MatchChecker[] dp) {
        dp[start].check(big[start + size - 1]);
        return dp[start].isCompleted();
    }

    void testMatchChecker() {
        int[] small = {1, 5, 9};
        Set<Integer> keys = createKeys(small);

        MatchChecker checker = new MatchChecker(keys);
        checker.check(1);
        System.out.println(checker.isCompleted());
        checker.check(5);
        System.out.println(checker.isCompleted());
        checker.check(11);
        System.out.println(checker.isCompleted());
        checker.check(9);
        System.out.println(checker.isCompleted());
    }

    Ex_17_18_ShortestSuperSequence() {
        int[] small = {5, 8, 7};
        int[] big = {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};
        Range result = solve(small, big);

        if (result != null) {
            for (int i = result.start; i <= result.end; ++i) {
                System.out.printf("%d(%d) ", big[i], i);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Ex_17_18_ShortestSuperSequence();
    }
}
