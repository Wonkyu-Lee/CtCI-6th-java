public class Ex_02_02_KthToLast {
    class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    class Index {
        int value;
    }

    Node kthToLast(Node node, int k) {
        return kthToLast(node, k, new Index());
    }

    Node kthToLast(Node node, int k, Index index) {
        if (node == null) {
            return null;
        }

        Node result = kthToLast(node.next, k, index);
        index.value++;

        if (k == index.value) {
            return node;
        }

        return result;
    }

    Node kthToLast2(Node node, int k) {
        Node p1 = node;
        Node p2 = node;

        for (int i = 0; i < k; ++i) {
            if (p1 == null)
                return null;
            p1 = p1.next;
        }

        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        return p2;
    }

    Node add(Node head, int value) {
        Node node = new Node(value);
        node.next = head;
        return node;
    }

    Ex_02_02_KthToLast() {
        // 5, 4, 3, 2, 1
        Node node = null;
        node = add(node, 1);
        node = add(node, 2);
        node = add(node, 3);
        node = add(node, 4);
        node = add(node, 5);

        Node kth = kthToLast(node, 3);
        System.out.println("kth node: " + kth.data);
    }

    public static void main(String args[]) {
        new Ex_02_02_KthToLast();
    }
}
