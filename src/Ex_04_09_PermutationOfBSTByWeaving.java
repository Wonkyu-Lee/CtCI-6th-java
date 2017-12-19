import java.util.ArrayList;
import java.util.LinkedList;

public class Ex_04_09_PermutationOfBSTByWeaving {
    class Node {
        final int value;
        Node left;
        Node right;
        Node(int value) { this.value = value; }
    }

    ArrayList<LinkedList<Integer>> allSequences(Node node) {
        ArrayList<LinkedList<Integer>> r = new ArrayList<>();
        if (node == null) {
            r.add(new LinkedList<>()); // TODO: 이게 없으면?
            return r;
        }

        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.addLast(node.value);

        ArrayList<LinkedList<Integer>> lSeq = allSequences(node.left);
        ArrayList<LinkedList<Integer>> rSeq = allSequences(node.right);
        for (LinkedList<Integer> first : lSeq) {
            for (LinkedList<Integer> second : rSeq) {
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<>();
                weave(weaved, first, second, prefix);
                r.addAll(weaved);
            }
        }

        return r;
    }

    void weave(
            ArrayList<LinkedList<Integer>> results,
            LinkedList<Integer> first,
            LinkedList<Integer> second,
            LinkedList<Integer> prefix) {
        if (first.isEmpty() || second.isEmpty()) {
            LinkedList<Integer> r = new LinkedList<>();
            r.addAll(prefix);
            r.addAll(first);
            r.addAll(second);
            results.add(r);
            return;
        }

        int firstHead = first.removeFirst();
        prefix.addLast(firstHead);
        weave(results, first, second, prefix);
        prefix.removeLast();
        first.addFirst(firstHead);

        int secondHead = second.removeFirst();
        prefix.addLast(secondHead);
        weave(results, first, second, prefix);
        prefix.removeLast();
        second.addFirst(secondHead);
    }

    Node addAll(int[] array, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        Node node = new Node(array[mid]);
        node.left = addAll(array, start, mid - 1);
        node.right = addAll(array, mid + 1, end);
        return node;
    }

    Node createMinimalBst(int[] array) {
        return addAll(array, 0, array.length - 1);
    }

    Ex_04_09_PermutationOfBSTByWeaving() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node node = createMinimalBst(array);
        ArrayList<LinkedList<Integer>> r = allSequences(node);
        for (LinkedList<Integer> list : r) {
            StringBuilder sb = new StringBuilder();
            for (int each : list) {
                sb.append(each);
                sb.append(" ");
            }
            System.out.println(sb.toString());
        }

        System.out.println("Count: " + r.size());
    }

    public static void main(String[] args) {
        new Ex_04_09_PermutationOfBSTByWeaving();
    }
}


