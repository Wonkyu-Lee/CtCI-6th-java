import java.util.HashSet;

public class Ex_02_08_FindLoopBeginning {
    class Node {
        Node next;
    }

    Node findLoopBeginning(Node head) {
        Node f = head;
        Node s = head;

        while (f != null && f.next != null) {
            f = f.next.next;
            s = s.next;
            if (f == s)
                break;
        }

        if (f == null || f.next == null)
            return null;

        s = head;
        while (f != s) {
            f = f.next;
            s = s.next;
        }

        return f;
    }

    static void main(String args[]) {

    }
}
