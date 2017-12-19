public class Ex_04_02_MinimalBst {
    static class Node {
        final int data;
        Node left;
        Node right;
        Node(int data) { this.data = data; }
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
}
