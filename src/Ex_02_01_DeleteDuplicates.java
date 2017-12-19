import java.util.HashSet;

public class Ex_02_01_DeleteDuplicates {
    static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
        }
    }

    static Node add(Node head, int value) {
        Node node = new Node(value);
        node.next = head;
        return node;
    }

    static void deleteDuplicates(Node node) {
        HashSet<Integer> set = new HashSet<>();
        Node previous = null;
        while (node != null) {
            if (set.contains(node.value)) {
                previous.next = node.next;
            } else {
                set.add(node.value);
                previous = node;
            }
            node = node.next;
        }
    }

    static void deleteDuplicates2(Node node) {
        Node current = node;
        while (current != null) {
            Node runner = current;
            while (runner.next != null) {
                if (runner.next.value == current.value) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }

    static void print(Node node) {
        Node current = node;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 4, 3, 1, 2};
        Node node = null;
        for (int each : array) {
            node = add(node, each);
        }

        print(node);
        deleteDuplicates2(node);
        print(node);
    }
}
