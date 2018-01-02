import java.util.*;

public class Ex_04_PrimMst {

    static class Edge<V extends Comparable<V>> {
        public final V first;
        public final V second;

        public Edge(V u, V v, boolean directed) {
            if (directed) {
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
            Edge other = (Edge)o;
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

        @Override
        public String toString() {
            Set<Edge<V>> edges = new HashSet<>();
            for (Node<V> node : nodes.values()) {
                V u = node.key;
                for (V v : node.adjs) {
                    edges.add(new Edge(u, v, directed));
                }
            }

            StringBuilder sb = new StringBuilder();
            for (Edge<V> edge : edges) {
                sb.append(edge);
            }

            sb.append(" ");

            return sb.toString();
        }
    }

    static <V extends Comparable<V>> Graph<V> primeMst(Map<Edge<V>, Integer> edgeLengths, V start) {
        Graph<V> graph = new Graph<>(false);
        for (Edge<V> edge : edgeLengths.keySet()) {
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
                int edgeLen = edgeLengths.get(new Edge(u, v, false));
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

    static <V extends Comparable<V>> Map<V, Path<V>> dijkstraSp(Map<Edge<V>, Integer> edgeLengths, V start) {
        Map<V, Path<V>> result = new HashMap<>();

        Graph<V> g = new Graph<>(true);
        for (Edge<V> edge : edgeLengths.keySet()) {
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

                int dist = d.get(u) + edgeLengths.get(new Edge(u, v, true));
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

    static <V extends Comparable<V>> Map<V, Path<V>> bellmanFord(Map<Edge<V>, Integer> edgeLengths, V start) {
        return null;
    }

    static void testPrim() {
        System.out.println("Prim MST:");
        Map<Edge<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Edge<>(0, 1, false), 8);
        edges.put(new Edge<>(0, 3, false), 10);
        edges.put(new Edge<>(1, 2, false), 9);
        edges.put(new Edge<>(1, 4, false), 11);
        edges.put(new Edge<>(2, 3, false), 5);
        edges.put(new Edge<>(2, 4, false), 13);
        edges.put(new Edge<>(2, 6, false), 12);
        edges.put(new Edge<>(4, 5, false), 8);
        edges.put(new Edge<>(5, 6, false), 7);

        Graph<Integer> mst = primeMst(edges, 1);
        System.out.println(mst);
    }

    static void testDijkstra() {
        System.out.println("Dijkstra Shortest Paths:");
        Map<Edge<Integer>, Integer> edges = new HashMap<>();
        edges.put(new Edge<>(0, 3, true), 10);
        edges.put(new Edge<>(1, 0, true), 8);
        edges.put(new Edge<>(1, 2, true), 9);
        edges.put(new Edge<>(1, 4, true), 11);
        edges.put(new Edge<>(2, 0, true), 6);
        edges.put(new Edge<>(2, 3, true), 1);
        edges.put(new Edge<>(2, 4, true), 3);
        edges.put(new Edge<>(3, 7, true), 2);
        edges.put(new Edge<>(4, 5, true), 8);
        edges.put(new Edge<>(4, 6, true), 8);
        edges.put(new Edge<>(5, 6, true), 7);
        edges.put(new Edge<>(6, 7, true), 5);
        edges.put(new Edge<>(7, 5, true), 4);

        Map<Integer, Path<Integer>> paths = dijkstraSp(edges, 1);
        for (Path<Integer> path : paths.values()) {
            System.out.println(path.toString());
        }
    }

    public static void main(String[] args) {
        testPrim();
        testDijkstra();
    }
}
