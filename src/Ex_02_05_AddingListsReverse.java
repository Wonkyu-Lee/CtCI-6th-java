public class Ex_02_05_AddingListsReverse {

    class Node {
        int value;
        Node next = null;
        Node(int value) {
            this.value = value;
        }
    }

    int countDigits(int x) {
        int count = 0;
        while (x / 10 > 0) {
            x /= 10;
            count++;
        }
        return count;
    }

    // 365: 3->6->5
    Node create(int x) {
        Node result = null;
        Node current = null;
        int nDigits = countDigits(x);
        for (int i = nDigits; 0 <= i; --i) {
            int d = (int)Math.pow(10, i);
            Node node = new Node(x / d);
            if (result == null)
                current = result = node;
            else {
                current.next = node;
                current = current.next;
            }
            x = x % d;
        }
        return result;
    }

    // 3->6->5: 365
    int convertToInt(Node node) {
        int result = 0;
        while (node != null) {
            result = 10 * result + node.value;
            node = node.next;
        }
        return result;
    }

    // 4617: 4->6->1->7
    // 295: 2->9->5
    int length(Node n) {
        int count = 0;
        while (n != null) {
            ++count;
            n = n.next;
        }
        return count;
    }

    Node fillZerosToFront(Node n, int zeros) {
        if (zeros <= 0) {
            return n;
        }

        Node r = new Node(0);
        Node current = r;
        --zeros;

        while (zeros > 0) {
            current.next = new Node(0);
            --zeros;
            current = current.next;
        }
        current.next = n;

        return r;
    }


    class Carry {
        int value = 0;
    }

    // 4617: 4->6->1->7
    // 0295: 0->2->9->5
    Node add(Node a, Node b) {
        int aLen = length(a);
        int bLen = length(b);

        if (aLen < bLen) {
            a = fillZerosToFront(a, bLen - aLen);
        } else if (bLen < aLen) {
            b = fillZerosToFront(b, aLen - bLen);
        }

        Carry carry = new Carry();
        Node c = add(a, b, carry);
        if (carry.value == 0) { // 확인: Integer와 int 비교 되나?
            return c;
        } else {
            Node r = new Node(carry.value);
            r.next = c;
            return r;
        }
    }

    Node add(Node a, Node b, Carry carry) {
        if (a == null && b == null) {
            return null;
        }

        Node next = add(a.next, b.next, carry);

        int sum = 0;
        if (a != null) sum += a.value;
        if (b != null) sum += b.value;
        sum += carry.value;

        Node r = new Node(sum % 10);
        r.next = next;
        carry.value = sum / 10;
        return r;
    }

    Ex_02_05_AddingListsReverse() {
        System.out.println(convertToInt(create(365)));
    }

    Ex_02_05_AddingListsReverse(int aValue, int bValue) {
        {
            Node a = create(aValue);
            Node b = create(bValue);
            Node c = add(a, b);
            System.out.printf("%d = %d + %d\n", convertToInt(c), aValue, bValue);
        }
    }

    public static void main(String args[]) {
        //new Ex_02_05_AddingListsReverse();

        new Ex_02_05_AddingListsReverse(0, 0);
        new Ex_02_05_AddingListsReverse(617, 395);
    }
}