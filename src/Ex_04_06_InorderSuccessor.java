public class Ex_04_06_InorderSuccessor {

    static class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        Node(int value) { this.value = value;}
    }

    static Node add(Node node, int value) {
        if (node == null) return new Node(value);

        if (value <= node.value) {
            node.left = add(node.left, value);
            node.left.parent = node;
        } else {
            node.right = add(node.right, value);
            node.right.parent = node;
        }

        return node;
    }

    static Node findInorderSuccessor(Node node) {
        if (node == null) return null;
        if (node.right != null) {
            // return left most child
            Node current = node.right;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        } else {
            // return parent when node is left child or null
            Node current = node;
            while (current.parent != null && current.parent.right == current) {
                current = current.parent;
            }
            return current.parent;
        }
    }

    interface NodeVisitor {
        void visit(Node node);
    }

    static void inorderTraverse(Node node, NodeVisitor visitor) {
        if (node == null) return;
        inorderTraverse(node.left, visitor);
        visitor.visit(node);
        inorderTraverse(node.right, visitor);
    }

    public static void main(String[] args) {
        int[] array = {10, 6, 2, 1, 5, 3, 4, 7, 8, 9, 11, 12};

        Node root = null;
        for (int each : array) {
            root = add(root, each);
        }

        inorderTraverse(root, (Node node) -> {
            Node successor = findInorderSuccessor(node);
            System.out.printf("%d's successor is %s\n", node.value, (successor != null ? successor.value : "null"));
        });
    }
}
