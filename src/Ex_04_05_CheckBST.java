public class Ex_04_05_CheckBST {

    static class Node {
        final int value;
        Node left;
        Node right;
        Node(int value) { this.value = value; }
    }

    static Node addAll(int[] array, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        Node node = new Node(array[mid]);
        node.left = addAll(array, start, mid - 1);
        node.right = addAll(array, mid + 1, end);
        return node;
    }

    static Node createMinimalBst(int[] array) {
        return addAll(array, 0, array.length - 1);
    }

    static boolean isBST(Node root) {
        return isBst(root, null, null);
    }

    static boolean isBst(Node node, Integer min, Integer max) {
        if (node == null) return true;

        if (min != null && min >= node.value) return false;
        if (max != null && node.value > max) return false;

        if (!isBst(node.left, min, node.value)) return false;
        if (!isBst(node.right, node.value, max)) return false;

        return true;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Node tree = createMinimalBst(array);

        boolean r = isBST(tree);
        System.out.println("Is BST?: " + r);
    }

}
