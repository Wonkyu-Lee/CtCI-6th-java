import java.util.*;

public class Notes {

    static void testModulo() {
        System.out.println((-12 % 5) + 5);
    }

    static void testSwap() {
        // Swap is impossible
    }

    static void testPriorityQueue() {
        // Default priority queue is like a min heap.
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(5);
        queue.add(2);
        queue.add(4);
        queue.add(1);
        queue.add(3);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    static void testIterator() {
        Integer[] array = {3, 5, 1, 4, 2};
        Collections.sort(Arrays.asList(array));
        for (int each : array) {
            System.out.println(each);
        }
    }

    static void testLinkedHashSet() {
        Set<Integer> set = new LinkedHashSet<>();
        Integer[] array = {3, 5, 1, 4, 2};
        for (int each : array) {
            set.add(each);
        }
        set.add(6);

//        for (int each : set) {
//            System.out.println(each);
//        }

        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void main(String args[]) {
        testLinkedHashSet();
    }
}
