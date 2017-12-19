import java.util.HashSet;

public class Ex_00_PairsOfDiffK {

    final static class OrderedPair {
        public final int first;
        public final int second;
        public OrderedPair(int first, int second) {

            if (first <= second) {
                this.first = first;
                this.second = second;
            } else {
                this.first = second;
                this.second = first;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o instanceof OrderedPair == false)
                return false;

            OrderedPair other = (OrderedPair)o;
            return (this.first == other.first && this.second == other.second);
        }

        @Override
        public int hashCode() {
            int r = 17;
            r = 31 * r + first;
            r = 31 * r + second;
            return r;
        }
    }

    static void findDiff(int[] array, int diff) {
        HashSet<OrderedPair> pairs = new HashSet<>();
        HashSet<Integer> numbers = new HashSet<>();
        for (int each : array) {
            numbers.add(each);
        }

        for (int each : array) {
            int lt = each - diff;
            int gt = each + diff;

            if (numbers.contains(lt)) {
                pairs.add(new OrderedPair(each, lt));
                pairs.add(new OrderedPair(each, gt));
            }
        }

        for (OrderedPair each : pairs) {
            System.out.printf("(%d, %d) ", each.first, each.second);
        }
        System.out.println();
    }

    public static void main(String args[]) {
        int[] array = {1, 7, 5, 9, 2, 12, 3};
        findDiff(array, 2);
    }
}
