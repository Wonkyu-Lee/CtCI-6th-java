import java.util.Collections;
import java.util.PriorityQueue;

public class Ex_00_Median {

    static class MedianRetriever {
        PriorityQueue<Double> mHeap1 = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Double> mHeap2 = new PriorityQueue<>();

        public void add(double value) {
            if (mHeap2.size() == 0 || value <= mHeap2.peek()) {
                mHeap1.add(value);
            } else {
                mHeap2.add(value);
            }

            ensureBalance();
        }

        public double getMedian() {
            if (mHeap1.size() == 0 && mHeap2.size() == 0) {
                return 0;
            }

            if (mHeap1.size() == mHeap2.size()) {
                return (mHeap1.peek() + mHeap2.peek()) / 2;
            }

            if (mHeap1.size() < mHeap2.size()) {
                return mHeap2.peek();
            }

            if (mHeap1.size() > mHeap2.size()) {
                return mHeap1.peek();
            }

            return 0;
        }

        private void ensureBalance() {
            if (mHeap1.size() > mHeap2.size() + 1) {
                mHeap2.add(mHeap1.poll());
            } else if (mHeap2.size() > mHeap1.size() + 1) {
                mHeap1.add(mHeap2.poll());
            }
        }
    }

    static void testHeap() {
        // Default: min heap, Reverse: max heap
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        heap.add(5);
        heap.add(2);
        heap.add(3);
        heap.add(1);

        while (!heap.isEmpty()) {
            int value = heap.poll();
            System.out.println(value);
        }
    }

    public static void main(String args[]) {
        MedianRetriever medianRetriever = new MedianRetriever();
        medianRetriever.add(5);
        medianRetriever.add(2);
        medianRetriever.add(3);
        medianRetriever.add(4);
        medianRetriever.add(1);
        medianRetriever.add(6);
        System.out.println("Median = " + medianRetriever.getMedian());
    }
}
