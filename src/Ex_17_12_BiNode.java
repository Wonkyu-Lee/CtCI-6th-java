public class Ex_17_12_BiNode {

    class BiNode {
        BiNode left;
        BiNode right;
        int value;
        BiNode(int value) { this.value = value; }
    }

    BiNode insert(BiNode node, int value) {
        if (node == null) {
            return new BiNode(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }

        return node;
    }

    BiNode convertTreeToList(BiNode node) {
        BiNode head = convert(node);
        if (head.left != null) {
            head.left.right = null;
            head.left = null;
        }

        return head;
    }

    BiNode convert(BiNode node) {
        if (node == null) return null;
        BiNode prev = convert(node.left);
        BiNode next = convert(node.right);

        BiNode head = node;
        BiNode tail = node;

        if (prev != null) {
            head = prev;
            node.left = prev.left;
            prev.left.right = node;
        }

        if (next != null) {
            tail = next.left;
            node.right = next;
            next.left = node;
        }

        head.left = tail;
        tail.right = head;

        return head;
    }

    Ex_17_12_BiNode() {
        BiNode tree = null;
        int[] values = {4, 2, 5, 1, 3, 6, 0};
        for (int i = 0; i < values.length; ++i) {
            tree =insert(tree, values[i]);
        }

        BiNode list = convertTreeToList(tree);

        {
            BiNode current = list;
            StringBuilder sb = new StringBuilder();
            while (current != null) {
                sb.append(current.value);
                sb.append(" ");
                current = current.right;
            }
            System.out.println(sb.toString());;
        }

        {
            BiNode current = list;
            StringBuilder sb = new StringBuilder();
            while (current.right != null) {
                current = current.right;
            }

            while(current != null) {
                sb.append(current.value);
                sb.append(" ");
                current = current.left;
            }
            System.out.println(sb.toString());;
        }
    }

    public static void main(String[] args) {
        new Ex_17_12_BiNode();
    }
}
