import java.util.EmptyStackException;
import java.util.Stack;

public class Ex_03_02_StackWithMin {
    static class StackWithMin<E extends Comparable> extends Stack<E> {
        private Stack<E> mMinStack = new Stack<>();

        public StackWithMin() {
            super();
        }

        @Override
        public E push(E e) {
            if (e instanceof Comparable == false) {
                throw new IllegalArgumentException();
            }

            if (mMinStack.isEmpty()) {
                mMinStack.push(e);
            } else {
                if (e.compareTo(mMinStack.peek()) <= 0) {
                    mMinStack.push(e);
                }
            }

            return super.push(e);
        }

        @Override
        public synchronized E pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            if (mMinStack.peek() == peek()) {
                mMinStack.pop();
            }

            return super.pop();
        }

        public E min() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            return mMinStack.peek();
        }
    }

    public static void main(String[] args) {
        StackWithMin<Integer> stack = new StackWithMin<>();

        int[] input = {5, 6, 3, 4, 3, 2};
        for (int each : input) {
            stack.push(each);
            System.out.printf("push(%d), min = %d\n", each, stack.min());
        }

        while(!stack.isEmpty()) {
            System.out.printf("min = %d, pop(%d)\n", stack.min(), stack.peek());
            stack.pop();
        }
    }
}
