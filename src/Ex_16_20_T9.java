import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ex_16_20_T9 {

    char[][] alphabets = {
            {},                   // 0
            {},                   // 1
            {'a', 'b', 'c'},      // 2
            {'d', 'e', 'f'},      // 3
            {'g', 'h', 'i'},      // 4
            {'j', 'k', 'l'},      // 5
            {'m', 'n', 'o'},      // 6
            {'p', 'q', 'r', 's'}, // 7
            {'t', 'u', 'v'},      // 8
            {'x', 'y', 'z'}       // 9
    };

    class TrieNode {
        final Map<Character, TrieNode> children = new HashMap<>();
        boolean terminates = false;

        void addWord(String word, int i) {
            if (i >= word.length()) {
                return;
            }

            TrieNode child = children.get(word.charAt(i));
            if (child == null) {
                child = new TrieNode();
                children.put(word.charAt(i), child);
            }

            if (i == word.length() - 1) {
                child.terminates = true;
            } else {
                child.addWord(word, i + 1);
            }
        }
    }

    TrieNode root = new TrieNode();

    void addWord(String word) {
        root.addWord(word, 0);
    }

    void buildTrie(String[] words) {
        for (int i = 0; i < words.length; ++i) {
            addWord(words[i]);
        }
    }

    List<String> findCandidates(int[] digits) {
        List<String> result = new LinkedList<>();
        findCandidates(root, digits, 0, new StringBuilder(), result);
        return result;
    }

    void findCandidates(TrieNode node, int[] digits, int i, StringBuilder sb, List<String> result) {
        if (node == null) {
            return;
        }

        if (i == digits.length) {
            if (node.terminates) {
                result.add(sb.toString());
            }
            return;
        }

        int digit = digits[i];
        for (int j = 0; j < alphabets[digit].length; ++j) {
            char ch = alphabets[digit][j];
            sb.append(ch);
            findCandidates(node.children.get(ch), digits, i + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    Ex_16_20_T9() {
        String[] words = {"tree", "used", "trie", "using", "use", "hello", "hell", "happy"};
        buildTrie(words);

        int[] digits = {8, 7, 3, 3};
        List<String> result = findCandidates(digits);
        for (String each : result) {
            System.out.println(each);
        }
    }

    public static void main(String[] args) {
        new Ex_16_20_T9();
    }
}
