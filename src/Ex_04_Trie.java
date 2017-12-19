import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Ex_04_Trie {
    static class Trie {
        private final Node mRoot = new Node();

        public Trie(Collection<String> list) {
            for (String each : list) {
                addWord(each);
            }
        }

        public Trie(String[] array) {
            this(Arrays.asList(array));
        }

        public boolean contains(String prefix, boolean exact) {
            Node node = mRoot;
            for (int i = 0; i < prefix.length(); ++i) {
                node = node.getChild(prefix.charAt(i));
                if (node == null) return false;
            }

            return exact ? node.terminates() : true;
        }

        public boolean contains(String prefix) {
            return contains(prefix, false);
        }

        public void addWord(String word) {
            mRoot.addWord(word);
        }

        private static class Node {
            boolean mTerminates;
            final Map<Character, Node> mChildren = new HashMap<>();

            Node() {}

            void addWord(String word) {
                if (word == null || word.isEmpty())
                    return;

                char ch = word.charAt(0);
                Node child = mChildren.get(ch);
                if (child == null) {
                    child = new Node();
                    mChildren.put(ch, child);
                }

                if (word.length() > 1) {
                    child.addWord(word.substring(1));
                } else {
                    child.setTerminates(true);
                }
            }

            boolean terminates() {
                return mTerminates;
            }

            void setTerminates(boolean terminates) {
                mTerminates = terminates;
            }

            Node getChild(char ch) {
                return mChildren.get(ch);
            }
        }
    }

    public static void main(String args[]) {
        String[] array = {"catch", "hello", "documentary"};
        Trie trie = new Trie(array);

        System.out.println("cat? " + trie.contains("cat") );
        System.out.println("cat exactly? " + trie.contains("cat", true) );
        System.out.println("hello exactly? " + trie.contains("hello", true) );
    }
}
