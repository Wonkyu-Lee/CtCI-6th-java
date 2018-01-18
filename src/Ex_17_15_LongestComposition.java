import java.util.*;

public class Ex_17_15_LongestComposition {

    int longestComposition(Set<String> dictionary) {
        ArrayList<String> word = new ArrayList<>(dictionary);
        PriorityQueue<String> maxHeap = new PriorityQueue<>((s1, s2) -> s2.length() - s1.length());
        maxHeap.addAll(word);

        while (!maxHeap.isEmpty()) {
            String longestWord = maxHeap.poll();
            if (isComposition(dictionary, longestWord)) {
                return longestWord.length();
            }
        }

        return 0;
    }

    boolean isComposition(Set<String> dictionary, String word) {
        return check(dictionary, word, 0, word.length() - 1) == Type.COMPOSITION;
    }

    // TODO: DP로 줄이자.
    enum Type {INVALID, WORD, COMPOSITION, EMPTY}
    Type check(Set<String> dictionary, String str, int s, int e) {
        if (s > e) {
            return Type.EMPTY;
        }

        String partial = str.substring(s, e + 1);
        Type result = Type.INVALID;
        if (dictionary.contains(partial)) {
            result = Type.WORD;
        }

        for (int i = s; i <= e - 1; ++i) {
            String substr = str.substring(s, i + 1);
            boolean validWord = dictionary.contains(substr);
            if (!validWord) {
                continue;
            }

            if (check(dictionary, str, i + 1, e) != Type.INVALID) {
                return Type.COMPOSITION;
            }
        }

        return result;
    }

    Ex_17_15_LongestComposition() {
        Set<String> dictionary = new HashSet<>();
        String[] words = {"dog", "cat", "banana", "dogcatnana", "nana", "walk", "walker", "dogwalker", "linearalgebra"};
        dictionary.addAll(Arrays.asList(words));
        System.out.printf("Longest composition: %d\n", longestComposition(dictionary));
    }

    public static void main(String[] args) {
        new Ex_17_15_LongestComposition();
    }
}
