import java.util.*;

public class Ex_17_22_WordsOneWay {

    Map<String, Set<String>> buildGraph(String[] dictionary) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (int i = 0; i < dictionary.length; ++i) {
            graph.put(dictionary[i], new HashSet<>());
            for (int j = 0; j < dictionary.length; ++j) {
                if (i != j && hasDiffOne(dictionary[i], dictionary[j])) {
                    graph.get(dictionary[i]).add(dictionary[j]);
                }
            }
        }
        return graph;
    }

    boolean hasDiffOne(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        int diffCount = 0;
        for (int i = 0; i < s1.length(); ++i) {
            if (s1.charAt(i) != s2.charAt(i)) {
                ++diffCount;
                if (diffCount > 1)
                    return false;
            }
        }

        return diffCount == 1;
    }

    boolean visit(Map<String, Set<String>> graph, Set<String> visited, LinkedList<String> path, String from, String to) {
        if (from.equals(to)) {
            path.addFirst(to);
            return true;
        }

        visited.add(from);

        for (String adj : graph.get(from)) {
            if (!visited.contains(adj)) {
                if (visit(graph, visited, path, adj, to)) {
                    path.addFirst(from);
                    return true;
                }
            }
        }

        return false;
    }

    List<String> getPath(String[] dictionary, String from, String to) {
        Map<String, Set<String>> graph = buildGraph(dictionary);

        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return Collections.emptyList();
        }

        // DFS
        LinkedList<String> path = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        visit(graph, visited, path, from, to);
        return path;
    }

    Ex_17_22_WordsOneWay() {
        String[] dictionary = {
                "LIMP",
                "DAMP",
                "LIKE",
                "LAMP",
                "LIME",
                "LOOK",
                "HELP",
                "HELL",
                "DELL",
        };

        List<String> path = getPath(dictionary, "DAMP", "LIKE");
        System.out.println("Path: ");
        for (String each : path) {
            System.out.printf("%s ", each);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new Ex_17_22_WordsOneWay();
    }
}
