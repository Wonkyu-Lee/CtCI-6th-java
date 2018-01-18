import java.util.*;

public class Ex_17_17_MultipleSearchUsingSuffixTrie {

    Map<String, List<Integer>> searchAll(String big, String[] smalls) {
        Map<String, List<Integer>> result = new HashMap<>();

        Trie trie = buildTrie(big);
        for (String small : smalls) {
            List<Integer> indices = trie.search(small);
            result.put(small, indices);
        }

        return result;
    }

    Trie buildTrie(String str) {
        Trie trie = new Trie();
        for (int i = 0; i < str.length(); ++i) {
            trie.insert(str.substring(i), i);
        }
        return trie;
    }

    class Trie {
        private Node root = new Node(' ');

        void insert(String str, int index) {
            root.insert(str, index);
        }

        List<Integer> search(String str) {
            Node current = root;
            for (int i = 0; i < str.length(); ++i) {
                current = current.children.get(str.charAt(i));
                if (current == null) {
                    return Collections.emptyList();
                }
            }
            return current.indices;
        }

        private class Node {
            char ch;
            Map<Character, Node> children = new HashMap<>();
            List<Integer> indices = new LinkedList<>();

            Node(char ch) {
                this.ch = ch;
            }

            void insert(String str, int index) {
                if (str == null || str.isEmpty()) {
                    return;
                }

                char first = str.charAt(0);
                Node child = children.get(first);
                if (child == null) {
                    child = new Node(first);
                    children.put(first, child);
                }
                child.indices.add(index);
                child.insert(str.substring(1), index);
            }
        }
    }

    Ex_17_17_MultipleSearchUsingSuffixTrie() {
        String big = "mississippi";
        String[] smalls = {"is", "ppi", "hi", "sis", "i", "ssippi"};
        Map<String, List<Integer>> found = searchAll(big, smalls);
        for (String key : found.keySet()) {
            System.out.printf("%s: ", key);
            List<Integer> indices = found.get(key);
            for (int index : indices) {
                System.out.printf("%d ", index);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Ex_17_17_MultipleSearchUsingSuffixTrie();
    }
}
