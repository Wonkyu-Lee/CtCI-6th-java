import java.util.ArrayList;
import java.util.HashMap;

public class Ex_00_Quadruples {

    final static class Pair {
        public final int first;
        public final int second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static void findQuadruples(int n) {
        HashMap<Integer, ArrayList<Pair>> table = new HashMap<>();
        for (int x = 1; x <= n; ++x) {
            for (int y = 1; y <= n; ++y) {
                int r = (x * x * x) + (y * y * y);
                if (!table.containsKey(r)) {
                    table.put(r, new ArrayList<>());
                }
                table.get(r).add(new Pair(x, y));
            }
        }

        for (int r : table.keySet()) {
            ArrayList<Pair> pairs = table.get(r);
            for (Pair pair1 : pairs) {
                for (Pair pair2 : pairs) {
                    System.out.printf("(%d, %d, %d, %d)\n", pair1.first, pair1.second, pair2.first, pair2.second);
                }
            }
        }
    }

    public static void main(String args[]) {
        findQuadruples(10);
    }
}
