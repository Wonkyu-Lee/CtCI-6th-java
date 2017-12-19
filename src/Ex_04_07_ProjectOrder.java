import java.util.*;

public class Ex_04_07_ProjectOrder {

    static class Graph<T> {
        private final Map<T, Vertex<T>> mVertices = new HashMap<>();
        private static class Vertex<T> {
            final T id;
            final Set<T> adjs = new HashSet<>();
            Vertex(T id) { this.id = id; }
        }

        public void addVertex(T id) {
            if (hasVertex(id)) return;
            mVertices.put(id, new Vertex(id));
        }

        public void addVertices(T[] array) {
            for (T each : array) {
                addVertex(each);
            }
        }

        public void addEdge(T uId, T vId) {
            if (!hasVertex(uId)) addVertex(uId);
            if (!hasVertex(vId)) addVertex(vId);
            Vertex<T> u = getVertex(uId);
            u.adjs.add(vId);
        }

        public void addEdges(T[][] array) {
            for (T[] each : array) {
                addEdge(each[0], each[1]);
            }
        }

        public List<T> topologicalOrder1() {
            List<T> order = new LinkedList<>();

            Map<T, Integer> inCounts = new HashMap<>();
            for (T id : mVertices.keySet()) {
                inCounts.put(id, 0);
            }

            for (Vertex<T> u : mVertices.values()) {
                for (T vId : u.adjs) {
                    inCounts.put(vId, inCounts.get(vId) + 1);
                }
            }

            Queue<Vertex<T>> queue = new LinkedList<>();
            for (T id : mVertices.keySet()) {
                if (inCounts.get(id) == 0)
                    queue.add(getVertex(id));
            }

            int vertexCount = getVertexCount();
            while (order.size() < vertexCount) {
                if (queue.isEmpty())
                    return new LinkedList<>(); // cycle!!!

                Vertex<T> u = queue.poll();
                order.add(u.id);
                for (T vId : u.adjs) {
                    inCounts.put(vId, inCounts.get(vId) - 1);
                    if (inCounts.get(vId) == 0) {
                        queue.add(getVertex(vId));
                    }
                }
            }

            return order;
        }

        static class DfsSort<T> {
            enum State { FRESH, VISITING, VISITED }
            final Map<T, State> states = new HashMap<>();
            final Graph<T> graph;
            final LinkedList<T> order = new LinkedList<>();

            public DfsSort(Graph<T> graph) {
                this.graph = graph;

                for (T id : graph.mVertices.keySet()) {
                    states.put(id, State.FRESH);
                }

                for (T id : graph.mVertices.keySet()) {
                    if (states.get(id) == State.FRESH) {
                        if (!doDfs(id)) {
                            order.clear();
                            return;
                        }
                    }
                }
            }

            public List<T> getOrder() {
                return order;
            }

            private boolean doDfs(T uId) {
                Vertex<T> u = graph.getVertex(uId);
                states.put(uId, State.VISITING);
                for (T vId : u.adjs) {
                    State state = states.get(vId);
                    if (state == State.VISITING) {
                        return false;
                    }
                    if (state == State.FRESH) {
                        doDfs(vId);
                    }
                }
                states.put(uId, State.VISITED);
                order.addFirst(uId);
                return true;
            }
        }

        public List<T> topologicalOrder2() {
            DfsSort dfsSort = new DfsSort(this);
            return dfsSort.getOrder();
        }

        public List<T> topologicalOrder() {
            Sort sort = new Sort();
            while (!sort.isFinished()) {
                sort.select(0);
            }
            return sort.getOrder();
        }

        private class Sort implements Cloneable {
            List<T> order = new LinkedList<>();
            Map<T, Integer> inCounts = new HashMap<>();
            ArrayList<Vertex<T>> candidates = new ArrayList<>();

            Sort() {
                this(false);
            }

            Sort(boolean empty) {
                if (empty) {
                    return;
                }

                for (T id : mVertices.keySet()) {
                    inCounts.put(id, 0);
                }

                for (Vertex<T> u : mVertices.values()) {
                    for (T vId : u.adjs) {
                        inCounts.put(vId, inCounts.get(vId) + 1);
                    }
                }

                for (T id : mVertices.keySet()) {
                    if (inCounts.get(id) == 0)
                        candidates.add(getVertex(id));
                }
            }

            @Override
            protected Sort clone() throws CloneNotSupportedException {
                Sort copy = new Sort(true);
                copy.order.addAll(order);
                copy.inCounts.putAll(inCounts);
                copy.candidates.addAll(candidates);
                return copy;
            }

            int getCandidateCount() {
                return candidates.size();
            }

            void select(int i) {
                if (i < 0 || candidates.size() <= i) throw new IndexOutOfBoundsException();

                Vertex<T> u = candidates.get(i);
                candidates.remove(i);
                order.add(u.id);
                for (T vId : u.adjs) {
                    inCounts.put(vId, inCounts.get(vId) - 1);
                    if (inCounts.get(vId) == 0) {
                        candidates.add(getVertex(vId));
                    }
                }
            }

            Sort cloneAndSelect(int i) {
                try {
                    Sort copy = clone();
                    copy.select(i);
                    return copy;
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException();
                }
            }

            List<T> getOrder() {
                return isSuccess() ? order : new LinkedList<>();
            }

            boolean isFinished() {
                return order.size() == getVertexCount() || candidates.isEmpty();
            }

            boolean isSuccess() {
                return order.size() == getVertexCount();
            }
        }

        void allTopologicalSortsRecurse(ArrayList<List<T>> result, Sort sort) {
            if (sort.isFinished()) {
                if (sort.isSuccess()) {
                    result.add(sort.getOrder());
                }
                return;
            }

            int candidatesCount = sort.getCandidateCount();
            for (int i = 0; i < candidatesCount; ++i) {
                allTopologicalSortsRecurse(result, sort.cloneAndSelect(i));
            }
        }

        public ArrayList<List<T>> allTopologicalOrders() {
            ArrayList<List<T>> result = new ArrayList<>();
            allTopologicalSortsRecurse(result, new Sort());
            return result;
        }

        private Vertex getVertex(T id) {
            return mVertices.get(id);
        }

        public boolean hasVertex(T id) {
            return mVertices.containsKey(id);
        }

        public int getVertexCount() {
            return mVertices.size();
        }
    }

    static <T> void printProjectOrder(T[] projects, T[][] dependencies) {
        Graph<T> g = new Graph<>();
        g.addVertices(projects);
        g.addEdges(dependencies);

        List<T> order = g.topologicalOrder2();
        StringBuilder sb = new StringBuilder();
        for (T each : order) {
            sb.append(each);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    static <T> void printAllProjectOrders(T[] projects, T[][] dependencies) {
        Graph<T> g = new Graph<>();
        g.addVertices(projects);
        g.addEdges(dependencies);
        ArrayList<List<T>> orders = g.allTopologicalOrders();
        for (List<T> list : orders) {
            StringBuilder sb = new StringBuilder();
            for (T each : list) {
                sb.append(each);
                sb.append(" ");
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        String[] projects = {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies = {{"a", "d"}, {"f", "b"}, {"b", "d"}, {"f", "a"}, {"d", "c"}};
        //String[][] dependencies = {{"a", "d"}, {"f", "b"}, {"b", "d"}, {"f", "a"}, {"d", "c"}, {"c", "b"}};

        printProjectOrder(projects, dependencies);
        //printAllProjectOrders(projects, dependencies);
    }
}
