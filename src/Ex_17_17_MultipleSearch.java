import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ex_17_17_MultipleSearch {

    Map<String, List<Integer>> searchAll(String big, String[] smalls) {
        Map<String, List<Integer>> result = new HashMap<>();

        for (String small : smalls) {
            List<Integer> indices = search(big, small);
            result.put(small, indices);
        }

        return result;
    }

    List<Integer> search(String big, String small) {
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i <= big.length() - small.length(); ++i) {
            if (match(big, i, small)) {
                result.add(i);
            }
        }
        return result;
    }

    boolean match(String big, int index, String small) {
        for (int i = 0; i < small.length(); ++i) {
            if (big.charAt(index + i) != small.charAt(i))
                return false;
        }
        return true;
    }

    Ex_17_17_MultipleSearch() {
        String big = "mississippi";
        String[] smalls = {"is", "ppi", "hi", "sis", "i", "ssippi"};
        Map<String, List<Integer>> found = searchAll(big, smalls);
        for (String key : found.keySet()) {
            System.out.printf("%s: ", key);
            List<Integer> indices = found.get(key);
            for (int index : indices) {
                System.out.printf("%d ", index);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Ex_17_17_MultipleSearch();
    }
}
