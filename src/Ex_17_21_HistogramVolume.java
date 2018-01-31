import java.util.ArrayList;

public class Ex_17_21_HistogramVolume {

    int getHistogramVolume(int[] histogram) {
        int max = 0;
        int indexAtMax = 0;
        int[] volume = new int[histogram.length];

        for (int i = 0; i < histogram.length; ++i) {
            if (histogram[i] == 0) {
                continue;
            }

            if (max == 0 && histogram[i] > 0) {
                max = histogram[i];
                indexAtMax = i;
                continue;
            }

            if (max <= histogram[i]) {
                for (int j = i - 1; indexAtMax < j; --j) {
                    volume[j] = max - histogram[j];
                }
                max = histogram[i];
                indexAtMax = i;
                continue;
            }

            if (0 < histogram[i] && histogram[i] < max) {
                for (int j = i - 1; histogram[j] < histogram[i]; --j) {
                    volume[j] = histogram[i] - histogram[j];
                }
                continue;
            }
        }

        int totalVolume = 0;
        for (int i = 0; i < volume.length; ++i) {
            totalVolume += volume[i];
            System.out.printf("[%d](%d), ", i, volume[i]);
        }
        System.out.println();

        return totalVolume;
    }

    int getHistogramVolume2(int[] histogram) {
        int volume = 0;

        int max = 0;
        ArrayList<Integer> P = new ArrayList<>();
        for (int i = 0; i < histogram.length; ++i) {
            if (histogram[i] > 0 && histogram[i] >= max) {
                max = histogram[i];
                P.add(i);
            }
        }
        for (int i = 0; i < P.size() - 1; ++i) {
            int p = P.get(i);
            int q = P.get(i + 1);
            for (int j = p + 1; j < q; ++j) {
                int amount = histogram[p] - histogram[j];
                volume += amount;
            }
        }

        max = 0;
        P.clear();
        for (int i = histogram.length - 1; i >= 0; --i) {
            if (histogram[i] > 0 && histogram[i] >= max) {
                max = histogram[i];
                P.add(i);
            }
        }

        for (int i = 0; i < P.size() - 1; ++i) {
            int p = P.get(i);
            int q = P.get(i + 1);
            for (int j = p - 1 ; j > q; --j) {
                int amount = histogram[p] - histogram[j];
                volume += amount;
            }
        }

        return volume;
    }


    Ex_17_21_HistogramVolume() {
        int histogram[] = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0};

        System.out.println("Total volume: " + getHistogramVolume(histogram));
        System.out.println("Total volume: " + getHistogramVolume2(histogram));
    }

    public static void main(String[] args) {
        new Ex_17_21_HistogramVolume();
    }
}
