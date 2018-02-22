public class Ex_08_05_RecursiveMultiplication {

    int mulRecurse(int l, int s) {
        if (s == 0) return 0;
        if (s == 1) return l;
        int q = s >> 1;
        int r = s - (q << 1);
        return (mulRecurse(l, q) << 1) + (r > 0 ? l : 0);
    }

    int mul(int x, int y) {
        int l, s;
        if (x >= y) {
            l = x;
            s = y;
        } else {
            l = y;
            s = x;
        }

        return mulRecurse(l, s);
    }

    Ex_08_05_RecursiveMultiplication() {
        System.out.println(mul(9, 7));
    }

    public static void main(String[] args) {
        new Ex_08_05_RecursiveMultiplication();
    }
}
