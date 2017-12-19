public class Ex_02_05_AddingLists {

    class Node {
        int value;
        Node next = null;
        Node(int value) {
            this.value = value;
        }
    }

    // 365: 5->6->3
    Node create(int x) {
        int value = x % 10;
        Node result = new Node(value);

        int y = x / 10;
        if (y != 0) {
            result.next = create(y);
        }

        return result;
    }

    // 5->6->3: 365
    int convertToInt(Node node) {
        if (node == null)
            return 0;

        int result = node.value;
        result += 10 * convertToInt(node.next);
        return result;
    }

    Node add(Node a, Node b, int carry) {
        if (a == null && b == null && carry == 0) {
            return null;
        }

        int sum = carry;
        Node aNext = null;
        Node bNext = null;
        if (a != null) {
            sum += a.value;
            aNext = a.next;
        }
        if (b != null) {
            sum += b.value;
            bNext = b.next;
        }

        Node next = add(aNext, bNext, sum / 10);
        Node c = new Node(sum % 10);
        c.next = next;
        return c;
    }

    Node add0(Node a, Node b, int carry) {
        if (a == null && b == null && carry == 0) {
            return null;
        }

        Node r = new Node(0);
        int sum = carry;
        if (a != null)
            sum += a.value;
        if (b != null)
            sum += b.value;
        r.value = sum % 10;

        if (a != null || b != null) {
            Node more = add0(
                    (a == null ? null : a.next),
                    (b == null ? null : b.next),
                    (sum >= 10 ? 1 : 0));
            r.next = more;
        }

        return r;
    }

    Ex_02_05_AddingLists(int aValue, int bValue) {

        {
            Node a = create(aValue);
            Node b = create(bValue);
            Node c = add0(a, b, 0);
            System.out.printf("%d = %d + %d\n", convertToInt(c), aValue, bValue);
        }

    }

    public static void main(String args[]) {
        //new Ex_02_05_AddingLists(0, 0);
        new Ex_02_05_AddingLists(617, 395);
    }
}