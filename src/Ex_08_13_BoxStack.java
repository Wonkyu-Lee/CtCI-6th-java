import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Ex_08_13_BoxStack {

    static class Box {
        final int w;
        final int d;
        final int h;
        Box(int w, int d, int h) {
            this.w = w;
            this.d = d;
            this.h = h;
        }

        boolean canBeAbove(Box other) {
            if (other == null)
                return true;

            return (w < other.w && d < other.d && h < other.h);
        }
    }

    Random random = new Random();
    Box createBox() {
        int minW = 1;
        int maxW = 10;
        int w = random.nextInt(maxW - minW) + minW;
        int d = random.nextInt(maxW - minW) + minW;
        int h = random.nextInt(maxW - minW) + minW;
        return new Box(w, d, h);
    }

    int createStack(ArrayList<Box> boxes) {
        Collections.sort(boxes, (b1, b2) -> (b2.h - b1.h)); // decreasing order
        return createStack(boxes, -1);
    }

    int createStack(ArrayList<Box> boxes, int index) {
        Box bottom = index == -1 ? null : boxes.get(index);
        int maxHeight = 0;
        for (int i = index + 1; i < boxes.size(); ++i) {
            if (boxes.get(i).canBeAbove(bottom)) {
                int height = createStack(boxes, i);
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
        }
        return maxHeight + (bottom == null ? 0 : bottom.h);
    }

    int createStackDp(ArrayList<Box> boxes) {
        Collections.sort(boxes, (b1, b2) -> (b1.h - b2.h)); // increasing order
        int[] dp = new int[boxes.size()];
        int maxHeight = 0;
        for (int i = 0; i < boxes.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (boxes.get(j).canBeAbove(boxes.get(i))) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i] += boxes.get(i).h;
            maxHeight = Math.max(maxHeight, dp[i]);
        }

        return maxHeight;
    }

    Ex_08_13_BoxStack() {
        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            boxes.add(createBox());
        }
        System.out.println(createStack(boxes));
        System.out.println(createStackDp(boxes));
    }

    public static void main(String[] args) {
        new Ex_08_13_BoxStack();
    }
}
