public class Ex_02_06_CheckPalindrome {

    class Node {
        int data;
        Node next;
        Node(int data) {
            this.data = data;
        }
    }

    final class Result {
        final boolean yes;
        final Node target;
        Result(boolean yes, Node target) {
            this.yes = yes;
            this.target = target;
        }
    }

    boolean isPalindrome(Node head) {
        return isPalindrome(head, getLength(head)).yes;
    }

    int getLength(Node node) {
        int count = 0;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    Result isPalindrome(Node node, int length) {
        if (length == 0) {
            return new Result(true, node);
        }

        if (length == 1) {
            return new Result(true, node.next);
        }

        Result r = isPalindrome(node.next, length - 2);
        if (!r.yes) {
            return new Result(false, null);
        } else if (r.target.data == node.data) {
            return new Result(true, r.target.next);
        } else {
            return new Result(false, null);
        }
    }

    Node toList(int[] array) {
        Node head = null;
        for (int i = array.length - 1; 0 <= i; --i) {
            Node node = new Node(array[i]);
            if (head == null)
                head = node;
            else {
                node.next = head;
                head = node;
            }
        }
        return head;
    }

    Ex_02_06_CheckPalindrome(int[] array) {
        Node head = toList(array);
        System.out.println("isPalindrome() = " + isPalindrome(head));
    }

    public static void main(String[] args) {
        {
            int[] array = {2, 5, 4, 4, 5, 2};
            new Ex_02_06_CheckPalindrome(array);
        }

        {
            int[] array = {2, 5, 4, 5, 2, 3};
            new Ex_02_06_CheckPalindrome(array);
        }

        {
            int[] array = {};
            new Ex_02_06_CheckPalindrome(array);
        }

        {
            int[] array = {5};
            new Ex_02_06_CheckPalindrome(array);
        }
    }
}
