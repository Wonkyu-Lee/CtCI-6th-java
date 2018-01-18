import java.util.*;

public class Ex_17_18_ShortestSuperSequence3 {
    class Range {
        final int start;
        final int end;
        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
        int length() {
            return end - start + 1;
        }
        boolean shorterThan(Range other) {
            return length() < other.length();
        }
    }

    Range solve(int[] small, int[] big) {
        ArrayList<Queue<Integer>> lists = getIndexLists(small, big);
        return getShortestClosure(lists);
    }

    ArrayList<Queue<Integer>> getIndexLists(int[] small, int[] big) {
        HashMap<Integer, Queue<Integer>> map = new HashMap<>();
        for (int i = 0; i < small.length; ++i) {
            map.put(small[i], new LinkedList<>());
        }

        for (int i = 0; i < big.length; ++i) {
            Queue<Integer> list = map.get(big[i]);
            if (list != null) {
                list.add(i);
            }
        }

        return new ArrayList<>(map.values());
    }

    class HeapNode implements Comparable<HeapNode> {
        int index;
        int from;

        HeapNode(int index, int from) {
            this.index = index;
            this.from = from;
        }

        @Override
        public int compareTo(HeapNode other) {
            return index - other.index;
        }
    }

    Range getShortestClosure(ArrayList<Queue<Integer>> lists) {
        PriorityQueue<HeapNode> heap = new PriorityQueue<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lists.size(); ++i) {
            if (lists.get(i).isEmpty()) {
                return null;
            }

            int index = lists.get(i).poll();
            heap.add(new HeapNode(index, i));
            min = Math.min(min, index);
            max = Math.max(max, index);
        }
        Range bestRange = new Range(min, max);

        while (true) {
            HeapNode node = heap.poll();
            Queue<Integer> list = lists.get(node.from);
            if (list.isEmpty()) {
                break;
            }

            int newIndex = list.poll();
            heap.add(new HeapNode(newIndex, node.from));
            min = heap.peek().index;
            max = Math.max(max, newIndex);
            Range newRange = new Range(min, max);
            if (newRange.shorterThan(bestRange)) {
                bestRange = newRange;
            }
        }

        return bestRange;
    }

    Ex_17_18_ShortestSuperSequence3() {
        int[] small = {1, 5, 9};
        int[] big = {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};
        Range result = solve(small, big);

        if (result != null) {
            for (int i = result.start; i <= result.end; ++i) {
                System.out.printf("%d(%d) ", big[i], i);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Ex_17_18_ShortestSuperSequence3();
    }
}
