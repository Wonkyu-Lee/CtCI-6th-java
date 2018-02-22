import java.util.HashSet;

public class Ex_16_11_DivingBoard {

    static class Solution1 {
        static HashSet<Integer> allLengths(int k, int longer, int shorter) {
            HashSet<Integer> lengths = new HashSet<>();
            allLengths(k, 0, longer, shorter, lengths);
            return lengths;
        }

        static void allLengths(int k, int totalLength, int longer, int shorter, HashSet<Integer> lengths) {
            if (k == 0) {
                lengths.add(totalLength);
                return;
            }

            allLengths(k - 1, totalLength + longer, longer, shorter, lengths);
            allLengths(k - 1, totalLength + shorter, longer, shorter, lengths);
        }
    }


    static class Solution2 {
        static HashSet<Integer> allLengths(int k, int longer, int shorter) {
            HashSet<Integer> lengths = new HashSet<>();
            HashSet<String> memo = new HashSet<>();
            allLengths(k, 0, longer, shorter, lengths, memo);
            return lengths;
        }

        static void allLengths(int k, int totalLength, int longer, int shorter, HashSet<Integer> lengths, HashSet<String> memo) {
            if (k == 0) {
                lengths.add(totalLength);
                return;
            }

            String key = k + " " + totalLength;
            if (memo.contains(key)) {
                return;
            }

            allLengths(k - 1, totalLength + longer, longer, shorter, lengths, memo);
            allLengths(k - 1, totalLength + shorter, longer, shorter, lengths, memo);

            memo.add(key);
        }
    }

    static class Solution3 {
        static HashSet<Integer> allLengths(int k, int longer, int shorter) {
            HashSet<Integer> lengths = new HashSet<>();

            for (int longerCount = 0; longerCount <= k; ++longerCount) {
                int shorterCount = k - longerCount;
                lengths.add(longerCount * longer + shorterCount * shorter);
            }

            return lengths;
        }
    }

    public static void main(String[] args) {
        System.out.println(Solution1.allLengths(7, 5, 3));
        System.out.println(Solution2.allLengths(7, 5, 3));
        System.out.println(Solution3.allLengths(7, 5, 3));
    }

}
