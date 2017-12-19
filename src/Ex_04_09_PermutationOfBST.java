import java.util.ArrayList;
import java.util.List;

public class Ex_04_09_PermutationOfBST {
    class Node {
        final int value;
        Node left;
        Node right;
        Node(int value) { this.value = value; }
    }

    class Tree {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Node> roots = new ArrayList<>();
        ArrayList<Node> order = new ArrayList<>();

        Tree(Node root) {
            addNodes(root);
            roots.add(root);
        }

        Tree(Tree other) {
            nodes = other.nodes;
            roots = (ArrayList<Node>)other.roots.clone();
            order = (ArrayList<Node>)other.order.clone();
        }

        void addNodes(Node node) {
            if (node == null) return;
            nodes.add(node);
            addNodes(node.left);
            addNodes(node.right);
        }

        boolean isFinished() { return roots.isEmpty(); }
        int getCandidateCount() { return roots.size(); }

        Tree cloneAndSelectNext(int i) {
            if (i < 0 || roots.size() <= i) return null;
            Tree tree = new Tree(this);
            tree.selectNext(i);
            return tree;
        }

        void selectNext(int i) {
            if (i < 0 || roots.size() <= i) throw new IndexOutOfBoundsException();
            Node node = roots.get(i);
            roots.remove(i);
            order.add(node);
            if (node.left != null) roots.add(node.left);
            if (node.right != null) roots.add(node.right);
        }

        List<Node> getOrder() { return order; }
    }

    ArrayList<List<Node>> findBSTPermutation(Node root) {
        ArrayList<List<Node>> result = new ArrayList<>();
        findBSTPermutation(result, new Tree(root));
        return result;
    }

    void findBSTPermutation(ArrayList<List<Node>> result, Tree tree) {
        if (tree.isFinished()) {
            result.add(tree.getOrder());
            return;
        }

        int count = tree.getCandidateCount();
        for (int i = 0; i < count; ++i) {
            Tree next = tree.cloneAndSelectNext(i);
            findBSTPermutation(result, next);
        }
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

    Ex_04_09_PermutationOfBST() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node node = createMinimalBst(array);
        ArrayList<List<Node>> r = findBSTPermutation(node);
        for (List<Node> list : r) {
            StringBuilder sb = new StringBuilder();
            for (Node each : list) {
                sb.append(each.value);
                sb.append(" ");
            }
            System.out.println(sb.toString());
        }

        System.out.println("Count: " + r.size());
    }

    public static void main(String[] args) {
        new Ex_04_09_PermutationOfBST();
    }
}
