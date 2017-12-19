import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Ex_04_01_PathExists {
    static class Graph {
        private Vertex[] mVertices;

        private class Vertex {
            final int id;
            Set<Integer> adjs = new HashSet<>();
            Vertex(int id) { this.id = id; }
        }

        public Graph(int vertexCount) {
            mVertices = new Vertex[vertexCount];
            for (int i = 0; i < vertexCount; ++i) {
                mVertices[i] = new Vertex(i);
            }
        }

        public void addEdge(int uId, int vId) {
            Vertex u = getVertex(uId);
            Vertex v = getVertex(vId);
            if (u == null || v == null) return;
            u.adjs.add(vId);
        }

        public boolean hasPath(int startId, int endId) {
            if (!hasVertex(startId)) return false;
            if (!hasVertex(endId)) return false;

            Vertex start = getVertex(startId);

            boolean[] visited = new boolean[getVertexCount()];

            Queue<Vertex> queue = new LinkedList<>();
            visited[start.id] = true;
            queue.add(start);

            while (!queue.isEmpty()) {
                Vertex u = queue.poll();
                if (endId == u.id)
                    return true;

                for (int vId : u.adjs) {
                    if (visited[vId] == false) {
                        visited[vId] = true;
                        queue.add(getVertex(vId));
                    }
                }
            }
            return false;
        }

        Vertex getVertex(int id) {
            if (!hasVertex(id)) return null;
            return mVertices[id];
        }

        boolean hasVertex(int id) {
            return 0 <= id && id < getVertexCount();
        }

        int getVertexCount() { return mVertices.length; }
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 5);
        g.addEdge(0, 4);

        System.out.printf("Path(%d->%d) exists? %s\n", 0, 5, Boolean.toString(g.hasPath(0, 5)));
        System.out.printf("Path(%d->%d) exists? %s\n", 1, 4, Boolean.toString(g.hasPath(1, 4)));
    }

}
