public class Ex_04_08_CommonAncestor {
    static class Node {
        final int value;
        Node left;
        Node right;
        boolean includes(Node node) {
            if (node == null) return false;
            if (node == this) return true;
            if (left != null && left.includes(node)) return true;
            if (right != null && right.includes(node)) return true;
            return false;
        }

        Node(int value) { this.value = value; }
    }

    static Node add(Node node, int value) {
        if (node == null) return new Node(value);

        if (value <= node.value) {
            node.left = add(node.left, value);
        } else {
            node.right = add(node.right, value);
        }

        return node;
    }

    static Node find(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            return find(node.left, value);
        } else if (value > node.value) {
            return find(node.right, value);
        } else {
            return node;
        }
    }

    static Node findCommonAncestor(Node root, Node p, Node q) {
        if (root == null) return null;
        if (!root.includes(p)) return null;
        if (!root.includes(q)) return null;
        if (p.includes(q)) return p;
        if (q.includes(p)) return q;
        Result r = findCommonRecurse(root, p, q);
        return r.found ? r.node : null;
    }

    static class Result {
        final Node node;
        final boolean found;
        Result(Node node, boolean found) {
            this.node = node;
            this.found = found;
        }
    }

    static Result findCommonRecurse(Node node, Node p, Node q) {
        if (node == p) return new Result(p, false);
        if (node == q) return new Result(q, false);
        if (node == null) return new Result(null, false);

        Result l = findCommonRecurse(node.left, p, q);
        if (l.found) return l;

        Result r = findCommonRecurse(node.right, p, q);
        if (r.found) return r;

        if ((l.node == p && r.node == q) || (l.node == q && r.node == p))
            return new Result(node, true);

        if (l.node == null) return r;
        else return l;
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 2, 1, 4, 8, 7, 6, 9};
        Node tree = null;

        for (int each : array) {
            tree = add(tree, each);
        }

        {
            Node n1 = find(tree, 1);
            Node n4 = find(tree, 4);
            Node common = findCommonAncestor(tree, n1, n4);
            System.out.printf("common of %d and %d is %d\n", n1.value, n4.value, common.value);
        }

    }
}
