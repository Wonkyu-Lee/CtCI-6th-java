import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class Ex_03_Queue {
    static class Queue<T> {
        private Node<T> mFirst;
        private Node<T> mLast;

        private static class Node<T> {
            T item;
            Node next;
            Node(T item) {
                this.item = item;
            }
        }

        public void add(T item) {
            Node<T> newNode = new Node<>(item);
            if (mFirst == null) {
                mFirst = mLast = newNode;
            } else {
                mLast.next = newNode;
                mLast = newNode;
            }
        }

        public T peek() {
            if (mFirst == null) {
                throw new NoSuchElementException();
            }

            return mFirst.item;
        }

        public T poll() {
            if (mFirst == null) {
                throw new NoSuchElementException();
            }

            T item = mFirst.item;
            mFirst = mFirst.next;
            if (mFirst == null) {
                mLast = null;
            }

            return item;
        }

        public boolean isEmpty() {
            return mFirst == null;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 5; ++i) {
            queue.add(i);
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
