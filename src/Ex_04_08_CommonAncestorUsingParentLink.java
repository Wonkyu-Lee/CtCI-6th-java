public class Ex_04_08_CommonAncestorUsingParentLink {
    class Node {
        final int value;
        Node left;
        Node right;
        Node parent;
        boolean includes(Node node) {
            while (node != null) {
                if (this == node) return true;
                node = node.parent;
            }
            return false;
        }

        int depth() {
            int d = 0;
            Node node = this;
            while (node != null) {
                node = node.parent;
                ++d;
            }
            return d;
        }

        Node(int value) { this.value = value; }
    }

    Node add(Node node, int value) {
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

    Node findCommonAncestor(Node root, Node p, Node q) {
        if (!root.includes(p)) return null;
        if (!root.includes(q)) return null;

        int pDepth = p.depth();
        int qDepth = q.depth();

        Node longer, shorter;
        if (pDepth >= qDepth) { longer = p; shorter = q; }
        else { longer = q; shorter = p; }

        int diff = Math.abs(pDepth - qDepth);
        for (int i = 0; i < diff; ++i) {
            longer = longer.parent;
        }

        while (longer != shorter) {
            longer = longer.parent;
            shorter = shorter.parent;
        }

        return longer;
    }

    Node find(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            return find(node.left, value);
        } else if (value > node.value) {
            return find(node.right, value);
        } else {
            return node;
        }
    }

    void checkCommonAncestor(Node tree, int value1, int value2) {
        Node n1 = find(tree, value1);
        Node n2 = find(tree, value2);
        Node common = findCommonAncestor(tree, n1, n2);
        System.out.printf("common of %d and %d is %s\n", n1.value, n2.value, (common == null ? "null" : String.valueOf(common.value)));
    }

    Ex_04_08_CommonAncestorUsingParentLink() {
        /**
         *                  5
         *             3          8
         *           2   4      7  9
         *         1           6
         */
        int[] array = {5, 3, 2, 1, 4, 8, 7, 6, 9};
        Node tree = null;

        for (int each : array) {
            tree = add(tree, each);
        }

        checkCommonAncestor(tree, 1, 4);
        checkCommonAncestor(tree, 3, 4);
        checkCommonAncestor(tree, 2, 6);
    }

    public static void main(String[] args) {
        new Ex_04_08_CommonAncestorUsingParentLink();
    }
}
