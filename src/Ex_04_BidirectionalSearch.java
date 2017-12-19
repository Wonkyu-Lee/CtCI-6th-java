import java.util.*;

public class Ex_04_BidirectionalSearch {

    public static class Graph<T> {
        private Map<T, Vertex<T>> mVertices = new HashMap<>();

        public void addEdge(T uId, T vId) {
            if (!hasVertex(uId)) mVertices.put(uId, new Vertex(uId));
            if (!hasVertex(vId)) mVertices.put(vId, new Vertex(vId));
            Vertex<T> u = mVertices.get(uId);
            Vertex<T> v = mVertices.get(vId);
            u.adjs.add(v);
            v.adjs.add(u);
        }

        public boolean hasVertex(T id) {
            return mVertices.containsKey(id);
        }

        public List<T> findPath(T uId, T vId) {
            if (!hasVertex(uId)) return null;
            if (!hasVertex(vId)) return null;

            Vertex<T> u = mVertices.get(uId);
            Vertex<T> v = mVertices.get(vId);

            Dfs uDfs = new Dfs(u);
            Dfs vDfs = new Dfs(v);

            while (!uDfs.isFinished() && !vDfs.isFinished()) {
                Vertex<T> contact;
                contact = uDfs.searchLevel(vDfs);
                if (contact != null) {
                    return uDfs.makePath(contact, vDfs);
                }
                contact = vDfs.searchLevel(uDfs);
                if (contact != null) {
                    return vDfs.makePath(contact, uDfs);
                }
            }

            return null;
        }

        private static class Vertex<T> {
            final T id;
            final Set<Vertex<T>> adjs = new HashSet<>();
            Vertex(T id) { this.id = id; }
        }

        private class Dfs {
            final Vertex<T> mStart;
            final Queue<Vertex<T>> mQueue = new LinkedList<>();
            final Set<T> mVisited = new HashSet<>();
            final Map<T, T> mPrev = new HashMap<>();

            Dfs(Vertex<T> start) {
                mStart = start;
                mVisited.add(start.id);
                mQueue.add(start);
            }

            boolean isFinished() {
                return mQueue.isEmpty();
            }

            boolean hasVisited(Vertex<T> v) {
                return mVisited.contains(v.id);
            }

            void visit(Vertex<T> v) {
                mVisited.add(v.id);
            }

            Vertex<T> searchLevel(Dfs other) {
                int count = mQueue.size();
                for (int i = 0; i < count; ++i) {
                    Vertex<T> u = mQueue.poll();
                    if (other.hasVisited(u)) return u;

                    for (Vertex<T> v : u.adjs) {
                        if (hasVisited(v)) continue;
                        mQueue.add(v);
                        visit(v);
                        mPrev.put(v.id, u.id);
                    }
                }

                return null;
            }

            List<T> makePath(Vertex<T> contact, Dfs other) {
                LinkedList<T> path = new LinkedList<>();

                T current = contact.id;
                while (current != null) {
                    path.addFirst(current);
                    current = mPrev.get(current);
                }

                current = other.mPrev.get(contact.id);
                while (current != null) {
                    path.add(current);
                    current = other.mPrev.get(current);
                }

                return path;
            }
        }
    }

    public static void main(String[] args) {
        Graph<Integer> g = new Graph<>();
        g.addEdge(1, 3);
        g.addEdge(3, 5);
        g.addEdge(9, 7);
        g.addEdge(5, 7);
        List<Integer> path = g.findPath(1, 9);
        if (path != null) {
            StringBuilder sb = new StringBuilder();
            for (int each : path) {
                sb.append(each);
                sb.append(", ");
            }
            sb.delete(sb.length() -2, sb.length() - 1);
            System.out.println(sb.toString());
        }
    }

}
