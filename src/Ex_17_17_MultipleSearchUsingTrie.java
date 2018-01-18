import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ex_17_17_MultipleSearchUsingTrie {
    Map<String, List<Integer>> searchAll(String big, String[] smalls) {
        Map<String, List<Integer>> result = new HashMap<>();
        for (int i = 0; i < smalls.length; ++i) {
            result.put(smalls[i], new LinkedList<>());
        }

        Trie trie = buildTrie(smalls, big.length());

        for (int i = 0; i < big.length(); ++i) {
            trie.collectWords(big, i, result);
        }

        return result;
    }

    Trie buildTrie(String[] words, int maxLength) {
        Trie trie = new Trie();
        for (int i = 0; i < words.length; ++i) {
            if (words[i].length() <= maxLength)
                trie.insert(words[i]);
        }

        return trie;
    }

    class Trie {
        private Node root = new Node();

        void insert(String word) {
            root.insert(word);
        }

        boolean contains(String word) {
            Node node = root;
            for (int i = 0; i < word.length(); ++i) {
                node = node.children.get(word.charAt(i));
                if (node == null)
                    return false;
            }

            return node.terminates;
        }

        void collectWords(String big, int startIndex, Map<String, List<Integer>> result) {
            Node node = root;
            String found = "";
            for (int i = startIndex; i < big.length(); ++i) {
                node = node.children.get(big.charAt(i));
                if (node == null) {
                    return;
                } else {
                    found += node.ch;
                    if (node.terminates) {
                        List<Integer> list = result.get(found);
                        if (list == null) {
                            list = new LinkedList<>();
                            result.put(found, list);
                        }
                        list.add(startIndex);
                    }
                }
            }
        }

        class Node {
            char ch = ' ';
            Map<Character, Node> children = new HashMap<>();
            boolean terminates = false;

            Node() {}
            Node(char ch) {
                this.ch = ch;
            }

            void insert(String word) {
                if (word == null || word.isEmpty())
                    return;

                char first = word.charAt(0);
                Node child = children.get(first);
                if (child == null) {
                    child = new Node(first);
                    children.put(first, child);
                }

                if (word.length() > 1) {
                    child.insert(word.substring(1));
                } else {
                    child.terminates = true;
                }
            }
        }
    }

    void testTrie() {
        Trie trie = new Trie();
        trie.insert("Hello");
        trie.insert("He");
        trie.insert("Hell");
        trie.insert("Welcome");
        trie.insert("Come");

        System.out.printf("trie.contains(\"Hell\") = %s\n", trie.contains("Hell"));
        System.out.printf("trie.contains(\"Hel\") = %s\n", trie.contains("Hel"));
        System.out.printf("trie.contains(\"He\") = %s\n", trie.contains("He"));
    }

    Ex_17_17_MultipleSearchUsingTrie() {
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
        new Ex_17_17_MultipleSearchUsingTrie();
    }
}
