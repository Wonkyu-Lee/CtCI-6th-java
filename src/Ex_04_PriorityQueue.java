import java.util.*;

public class Ex_04_PriorityQueue {
    static class PriorityQueue<T> {
        private ArrayList<T> mItems = new ArrayList<>();
        private Comparator<T> mComparator;

        public PriorityQueue() {
        }

        public PriorityQueue(Comparator<T> comparator) {
            mComparator = comparator;
        }

        public PriorityQueue(Collection<T> collection) {
            mItems.addAll(collection);
            buildHeap();
        }

        public PriorityQueue(Collection<T> collection, Comparator<T> comparator) {
            mItems.addAll(collection);
            buildHeap();
            mComparator = comparator;
        }

        public boolean isEmpty() {
            return mItems.size() == 0;
        }

        public int size() {
            return mItems.size();
        }

        public void add(T item) {
            mItems.add(item);
            int index = mItems.size() - 1;
            int parentIndex = getParentIndex(index);
            while (isValidIndex(parentIndex)) {
                heapify(parentIndex);
                parentIndex = getParentIndex(parentIndex);
            }
        }

        public T poll() {
            if (isEmpty()) return null;
            T item = mItems.get(0);
            int lastIndex = mItems.size() - 1;
            swap(0, lastIndex);
            mItems.remove(lastIndex);
            heapify(0);
            return item;
        }

        private void heapify(int index) {
            if (!isValidIndex(index)) return;
            int left = getLeftChildIndex(index);
            int right = getRightChildIndex(index);
            int smaller;
            if (isValidIndex(right)) {
                smaller = (compare(left, right) < 0) ? left : right;
            } else if (isValidIndex(left)) {
                smaller = left;
            } else {
                return;
            }

            if (compare(index, smaller) > 0) {
                swap(index, smaller);
                heapify(smaller);
            }
        }

        private int getParentIndex(int index) {
            if (index == 0) return -1;
            return (index - 1) / 2;
        }

        private int getLeftChildIndex(int index) {
            return (index + 1) * 2 -1;
        }

        private int getRightChildIndex(int index) {
            return (index + 1) * 2;
        }

        private boolean isValidIndex(int index) {
            return 0 <= index && index < size();
        }

        private int compare(int i, int j) {
            T iItem = mItems.get(i);
            T jItem = mItems.get(j);

            if (mComparator == null) {
                return ((Comparable<T>)iItem).compareTo(jItem);
            } else {
                return mComparator.compare(iItem, jItem);
            }
        }

        private void swap(int i, int j) {
            T temp = mItems.get(i);
            mItems.set(i, mItems.get(j));
            mItems.set(j, temp);
        }

        private void buildHeap() {
            int lastIndex = size() - 1;
            int lastParentIndex = getParentIndex(lastIndex);
            for (int i = lastParentIndex; 0 <= i; --i) {
                heapify(i);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = {5, 2, 6, 2, 4, 8, 1};
        PriorityQueue<Integer> queue = new PriorityQueue<>(Arrays.asList(array), Collections.reverseOrder());

        while (!queue.isEmpty()) {
            int value = queue.poll();
            System.out.println(value);
        }
    }
}
