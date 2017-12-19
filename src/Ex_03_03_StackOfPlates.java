import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;

public class Ex_03_03_StackOfPlates {

    static class SetOfStacks<T> {
        private final ArrayList<Stack<T>> mStacks = new ArrayList<>();
        private final int mCapacity;

        private class Stack<T> {
            final LinkedList<T> mList = new LinkedList<>();

            boolean isFull() {
                return mList.size() == mCapacity;
            }

            boolean isEmpty() {
                return mList.isEmpty();
            }

            boolean push(T item) {
                if (isFull()) return false;
                mList.add(item);
                return true;
            }

            T pop() {
                if (isEmpty()) throw new EmptyStackException();
                return mList.pollLast();
            }

            T popBottom() {
                if (isEmpty()) throw new EmptyStackException();
                return mList.pollFirst();
            }
        }

        public SetOfStacks(int capacity) {
            if (capacity <= 0) throw new IllegalArgumentException();
            mCapacity = capacity;
        }

        public boolean isEmpty() {
            return mStacks.size() == 0;
        }

        public void push(T item) {
            Stack<T> stack = getLastStack();
            if (stack == null || stack.isFull()) {
                mStacks.add(new Stack());
            }
            stack = getLastStack();
            stack.push(item);
        }

        public T pop() {
            Stack<T> stack = getLastStack();
            if (stack == null) throw new EmptyStackException();
            T item = stack.pop();
            if (stack.isEmpty()) mStacks.remove(mStacks.size() - 1);
            return item;
        }

        public T popAt(int stackIndex) {
            T item = leftShift(stackIndex, true);
            return item;
        }

        private T leftShift(int stackIndex, boolean removeTop) {
            if (stackIndex < 0 || mStacks.size() <= stackIndex)
                throw new IndexOutOfBoundsException();

            Stack<T> stack = mStacks.get(stackIndex);
            T item = removeTop ? stack.pop() : stack.popBottom();

            if (stack.isEmpty()) {
                mStacks.remove(stackIndex);
            } else if (stackIndex + 1 < mStacks.size()) {
                T carry = leftShift(stackIndex + 1, false);
                stack.push(carry);
            }

            return item;
        }

        private Stack<T> getLastStack() {
            if (mStacks.isEmpty()) return null;
            return mStacks.get(mStacks.size() - 1);
        }
    }

    public static void main(String[] args) {
        SetOfStacks<Integer> stacks = new SetOfStacks<>(3);

        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int each : input) {
            stacks.push(each);
        }

        int value;
        value = stacks.popAt(1);
        System.out.printf("%d ", value);

        value = stacks.popAt(1);
        System.out.printf("%d ", value);

        value = stacks.popAt(1);
        System.out.printf("%d ", value);
    }

}
