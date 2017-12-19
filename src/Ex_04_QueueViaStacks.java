import java.util.Stack;

public class Ex_04_QueueViaStacks {

    static class EmptyQueueException extends RuntimeException {}

    static class Queue<T> {
        private Stack<T> mInStack = new Stack<>();
        private Stack<T> mOutStack = new Stack<>();

        public boolean isEmpty() {
            return mInStack.isEmpty() && mOutStack.isEmpty();
        }

        public void add(T item) {
            mInStack.push(item);
        }

        public T peek() {
            if (isEmpty()) throw new EmptyQueueException();
            move();
            return mOutStack.peek();
        }

        public T poll() {
            if (isEmpty()) throw new EmptyQueueException();
            move();
            return mOutStack.pop();
        }

        private void move() {
            if (mOutStack.isEmpty()) {
                while (!mInStack.isEmpty()) {
                    mOutStack.push(mInStack.pop());
                }
            }
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 10; ++i) {
            queue.add(i);
        }

        while (!queue.isEmpty()) {
            int i = queue.poll();
            System.out.printf("%d ", i);
        }
        System.out.println();
    }

}
