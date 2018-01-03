import java.util.*;

public class Ex_04_PrimMst {

    static class Pair<V extends Comparable<V>> {
        public final V first;
        public final V second;

        public Pair(V u, V v, boolean ordered) {
            if (ordered) {
                first = u;
                second = v;
            } else {
                if (u.compareTo(v) <= 0) {
                    first = u;
                    second = v;
                } else {
                    first = v;
                    second = u;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            Pair other = (Pair)o;
            if (other == null) return false;
            if (this == other) return true;
            return (this.first == other.first && this.second == other.second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() ^ second.hashCode();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(first);
            sb.append(",");
            sb.append(second);
            sb.append(")");
            return sb.toString();
        }
    }

    static class Graph<V extends Comparable<V>> {
        private static class Node<V> {
            public final V key;
            Set<V> adjs = new HashSet<>();
            public Node(V key) { this.key = key; }
        }

        private final Map<V, Node> nodes = new HashMap<>();
        private final boolean directed;

        public Graph(boolean directed) {
            this.directed = directed;
        }

        public boolean addVertex(V v) {
            if (nodes.containsKey(v))
                return false;
            nodes.put(v, new Node<V>(v));
            return true;
        }

        public boolean hasVertex(V v) {
            return nodes.containsKey(v);
        }

        public boolean addEdge(V u, V v) {
            if (hasEdge(u, v))
                return false;

            addVertex(u);
            addVertex(v);
            nodes.get(u).adjs.add(v);

            if (!directed)
                nodes.get(v).adjs.add(u);

            return true;
        }

        public boolean hasEdge(V u, V v) {
            if (!hasVertex(u)) return false;
            if (!hasVertex(v)) return false;
            return (nodes.get(u).adjs.contains(v));
        }

        public Set<V> getAdjacencies(V v) {
            if (!hasVertex(v)) return null;
            return new HashSet<>(nodes.get(v).adjs);
        }

        public Set<V> getVertices() {
            return nodes.keySet();
        }

        public int getVertexCount() {
            return nodes.size();
        }

        @Override
        public String toString() {
            Set<Pair<V>> edges = new HashSet<>();
            for (Node<V> node : nodes.values()) {
                V u = node.key;
                for (V v : node.adjs) {
                    edges.add(new Pair(u, v, directed));
                }
            }

            StringBuilder sb = new StringBuilder();
            for (Pair<V> edge : edges) {
                sb.append(edge);
            }

            sb.append(" ");

            return sb.toString();
        }
    }

    static <V extends Comparable<V>> Graph<V> primeMst(Map<Pair<V>, Integer> edgeLengths, V start) {
        Graph<V> graph = new Graph<>(false);
        for (Pair<V> edge : edgeLengths.keySet()) {
            graph.addEdge(edge.first, edge.second);
        }

        if (!graph.hasVertex(start))
            return null;

        Map<V, Integer> d = new HashMap<>();
        for (V vertex : graph.getVertices()) {
            d.put(vertex, Integer.MAX_VALUE);
        }
        d.put(start, 0);

        PriorityQueue<V> Q = new PriorityQueue<>(Comparator.comparing((v)->d.get(v)));
        Q.addAll(graph.getVertices());

        Map<V, V> parents = new HashMap<>();
        while (!Q.isEmpty()) {
            V u = Q.poll();
            for (V v : graph.getAdjacencies(u)) {
                if (!Q.contains(v)) continue;
                int edgeLen = edgeLengths.get(new Pair(u, v, false));
                if (edgeLen < d.get(v)) {
                    d.put(v, edgeLen);
                    Q.remove(v); Q.add(v);
                    parents.put(v, u);
                }
            }
        }

        Graph<V> result = new Graph<>(false);
        for (V v : parents.keySet()) {
            V parent = parents.get(v);
            result.addEdge(parent, v);
        }

        return result;
    }

    static class Path<V> {
        final int length;
        final List<V> vertices;
        Path(int length, List<V> vertices) { this.length = length; this.vertices = vertices; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("[%d] ", length));
            boolean atStart = true;
            for (V v : vertices) {
                if (atStart) {
                    atStart = false;
                } else {
                    sb.append("->");
                }
                sb.append(v);
            }

            return sb.toString();
        }
    }

    static <V extends Comparable<V>> Map<V, Path<V>> dijkstraSp(Map<Pair<V>, Integer> edgeLengths, V start) {
        Map<V, Path<V>> result = new HashMap<>();

        Graph<V> g = new Graph<>(true);
        for (Pair<V> edge : edgeLengths.keySet()) {
            g.addEdge(edge.first, edge.second);
        }

        if (!g.hasVertex(start))
            return null;

        Map<V, Integer> d = new HashMap<>();
        for (V v : g.getVertices()) {
            d.put(v, Integer.MAX_VALUE);
        }
        d.put(start, 0);

        PriorityQueue<V> Q = new PriorityQueue<>(Comparator.comparing(v -> d.get(v)));
        Q.addAll(g.getVertices());

        Map<V, V> prev = new HashMap<>();
        Map<V, Integer> distance = new HashMap<>();
        while (!Q.isEmpty()) {
            V u = Q.poll();

            for (V v : g.getAdjacencies(u)) {
                if (!Q.contains(v)) continue;

                int dist = d.get(u) + edgeLengths.get(new Pair(u, v, true));
                if (dist < d.get(v)) {
                    d.put(v, dist);
                    Q.remove(v); Q.add(v);

                    prev.put(v, u);
                    distance.put(v, dist);
                }
            }
        }

        for (V v : g.getVertices()) {
            V current = v;
            LinkedList<V> path = new LinkedList<>();
            path.addFirst(current);

            while (prev.containsKey(current)) {
                current = prev.get(current);
                path.addFirst(current);
            }

            if (distance.containsKey(v))
                result.put(v, new Path<>(distance.get(v), path));
        }

        return result;
    }

    static <V extends Comparable<V>> Map<V, Path<V>> bellmanFord(Map<Pair<V>, Integer> edgeLengths, V start) {
        Map<V, Path<V>> result = new HashMap<>();

        Graph<V> g = new Graph(true);
        for (Pair<V> edge : edgeLengths.keySet()) {
            g.addEdge(edge.first, edge.second);
        }

        if (!g.hasVertex(start))
            return null;

        Map<V, Integer> d0 = new HashMap<>();
        for (V v : g.getVertices()) {
            d0.put(v, Integer.MAX_VALUE);
        }
        d0.put(start, 0);

        Map<V, Integer> d = new HashMap<>();
        for (V v : g.getVertices()) {
            d.put(v, d0.get(v));
        }

        Map<V, V> prev = new HashMap<>();

        Set<V> vertices = g.getVertices();
        for (int i = 0; i < vertices.size() - 1; ++i) {
            for (V u : vertices) {
                for (V v : g.getAdjacencies(u)) {
                    if (d0.get(u) == Integer.MAX_VALUE)
                        continue;

                    int dVFromU = d0.get(u) + edgeLengths.get(new Pair<>(u, v, true));
                    if (dVFromU < d0.get(v)) {
                        d.put(v, dVFromU);
                        prev.put(v, u);
                    }
                }
            }

            for (V v : g.getVertices()) {
                d0.put(v, d.get(v));
            }
        }

        // if minus cycle found, return null
        for (V u : vertices) {
            for (V v : g.getAdjacencies(u)) {
                int dVFromU = d0.get(u) + edgeLengths.get(new Pair<>(u, v, true));
                if (dVFromU < d0.get(v)) {
                    return null;
                }
            }
        }

        for (V v : g.getVertices()) {
            V current = v;
            LinkedList<V> path = new LinkedList<>();
            path.addFirst(current);

            while (prev.containsKey(current)) {
                current = prev.get(current);
                path.addFirst(current);
            }

            if (d.containsKey(v))
                result.put(v, new Path<>(d.get(v), path));
        }

        return result;
    }

    // mid[k] = { v[0], v[1], v[k] }
    // d[i, j, k]
    //    = w[i, j], if k = 0
    //    = min(d[i, j, k -1], d[i, k, k - 1] + d[k, j, k - 1]), if k >= 1
    static <V extends Comparable<V>> Map<Pair<V>, Path<V>> floydWarshall(Map<Pair<V>, Integer> edgeLengths) {
        Map<Pair<V>, Path<V>> result = new TreeMap<>((p1, p2) -> {
            if (p1.first.compareTo(p2.first) == 0) {
                return p1.second.compareTo(p2.second);
            } else {
                return p1.first.compareTo(p2.first);
            }
        });

        Graph<V> g = new Graph<>(true);
        for (Pair<V> edge : edgeLengths.keySet()) {
            g.addEdge(edge.first, edge.second);
        }


        ArrayList<V> v = new ArrayList<>();
        v.addAll(g.getVertices());
        int n = v.size();

        int d[][][] = new int[n][n][n + 1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                Pair<V> edge = new Pair<>(v.get(i), v.get(j), true);
                int distance = edgeLengths.getOrDefault(edge, Integer.MAX_VALUE);
                d[i][j][0] = distance;
                if (distance != Integer.MAX_VALUE) {
                    List<V> vertices = new LinkedList<>();
                    vertices.add(v.get(i));
                    vertices.add(v.get(j));
                    result.put(edge, new Path<>(distance, vertices));
                }
            }
        }

        for (int k = 1; k <= n; ++k) {
            int kth = k - 1;

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    int left = d[i][j][k - 1];
                    int right = Integer.MAX_VALUE;

                    if (d[i][kth][k - 1] != Integer.MAX_VALUE && d[kth][j][k - 1] != Integer.MAX_VALUE) {
                        right = d[i][kth][k - 1] + d[kth][j][k - 1];
                    }

                    if (left <= right) {
                        d[i][j][k] = left;
                    } else {
                        d[i][j][k] = right;

                        Pair<V> edge_ik = new Pair<>(v.get(i), v.get(kth), true);
                        Pair<V> edge_kj = new Pair<>(v.get(kth), v.get(j), true);

                        Path<V> path_ik = result.get(edge_ik);
                        Path<V> path_kj = result.get(edge_kj);

                        LinkedList<V> vertices = new LinkedList<>();
                        vertices.addAll(path_ik.vertices);
                        vertices.removeLast(); // remove repeated k
                        vertices.addAll(path_kj.vertices);
                        Path<V> path_ij = new Path<>(right, vertices);
                        Pair<V> edge_ij = new Pair<>(v.get(i), v.get(j), true);
                        result.put(edge_ij, path_ij);
                    }
                }
            }
        }

        return result;
    }

    static void testPrim() {
        System.out.println("Prim MST:");

        Map<Pair<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Pair<>(0, 1, false), 8);
        edges.put(new Pair<>(0, 3, false), 10);
        edges.put(new Pair<>(1, 2, false), 9);
        edges.put(new Pair<>(1, 4, false), 11);
        edges.put(new Pair<>(2, 3, false), 5);
        edges.put(new Pair<>(2, 4, false), 13);
        edges.put(new Pair<>(2, 6, false), 12);
        edges.put(new Pair<>(4, 5, false), 8);
        edges.put(new Pair<>(5, 6, false), 7);

        Graph<Integer> mst = primeMst(edges, 1);
        System.out.println(mst);
    }

    static void testDijkstra() {
        System.out.println("Dijkstra Shortest Paths:");
        Map<Pair<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Pair<>(0, 3, true), 10);
        edges.put(new Pair<>(1, 0, true), 8);
        edges.put(new Pair<>(1, 2, true), 9);
        edges.put(new Pair<>(1, 4, true), 11);
        edges.put(new Pair<>(2, 0, true), 6);
        edges.put(new Pair<>(2, 3, true), 1);
        edges.put(new Pair<>(2, 4, true), 3);
        edges.put(new Pair<>(3, 7, true), 2);
        edges.put(new Pair<>(4, 5, true), 8);
        edges.put(new Pair<>(4, 6, true), 8);
        edges.put(new Pair<>(5, 6, true), 7);
        edges.put(new Pair<>(6, 7, true), 5);
        edges.put(new Pair<>(7, 5, true), 4);

        Map<Integer, Path<Integer>> paths = dijkstraSp(edges, 1);
        for (Path<Integer> path : paths.values()) {
            System.out.println(path.toString());
        }
    }

    static void testBellmanFord() {
        System.out.println("Bellman-Ford Shortest Paths:");
        Map<Pair<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Pair<>(0, 3, true), 10);
        edges.put(new Pair<>(1, 0, true), 8);
        edges.put(new Pair<>(1, 2, true), 9);
        edges.put(new Pair<>(1, 4, true), 11);
        edges.put(new Pair<>(2, 0, true), -15);
        edges.put(new Pair<>(2, 3, true), 1);
        edges.put(new Pair<>(2, 4, true), 3);
        edges.put(new Pair<>(3, 7, true), 2);
        edges.put(new Pair<>(4, 5, true), 8);
        edges.put(new Pair<>(4, 6, true), 8);
        edges.put(new Pair<>(5, 6, true), -7);
        edges.put(new Pair<>(6, 7, true), 5);
        edges.put(new Pair<>(7, 5, true), 4);

        Map<Integer, Path<Integer>> paths = bellmanFord(edges, 1);
        for (Path<Integer> path : paths.values()) {
            System.out.println(path.toString());
        }
    }

    static void testFloydWarshall() {
        System.out.println("Floyd-Warshall Shortest Paths:");
        Map<Pair<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Pair<>(0, 3, true), 10);
        edges.put(new Pair<>(1, 0, true), 8);
        edges.put(new Pair<>(1, 2, true), 9);
        edges.put(new Pair<>(1, 4, true), 11);
        edges.put(new Pair<>(2, 0, true), -15);
        edges.put(new Pair<>(2, 3, true), 1);
        edges.put(new Pair<>(2, 4, true), 3);
        edges.put(new Pair<>(3, 7, true), 2);
        edges.put(new Pair<>(4, 5, true), 8);
        edges.put(new Pair<>(4, 6, true), 8);
        edges.put(new Pair<>(5, 6, true), -7);
        edges.put(new Pair<>(6, 7, true), 5);
        edges.put(new Pair<>(7, 5, true), 4);

        Map<Pair<Integer>, Path<Integer>> allPaths = floydWarshall(edges);
        for (Pair<Integer> pair : allPaths.keySet()) {
            System.out.println(pair + ": " + allPaths.get(pair));
        }
    }

    public static void main(String[] args) {
        testPrim();
        testDijkstra();
        testBellmanFord();
        testFloydWarshall();
    }
}