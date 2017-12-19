import java.util.Stack;

public class Ex_03_05_SortStack {
    static void sortStack(Stack<Integer> s) {
        if (s == null) throw new NullPointerException();

        Stack<Integer> r = new Stack<>();
        while (!s.isEmpty()) {
            int t = s.pop();
            while (!r.isEmpty() && r.peek() > t) {
                s.push(r.pop());
            }
            r.push(t);
        }

        while (!r.isEmpty()) {
            s.push(r.pop());
        }
    }

    public static void main(String[] args) {
        int[] array = {2, 5, 1, 4, 3, 7, 6};
        Stack<Integer> s = new Stack<>();
        for (int each : array) s.push(each);
        sortStack(s);
        while (!s.isEmpty()) {
            System.out.printf("%d ", s.pop());
        }
        System.out.println();
    }
}
