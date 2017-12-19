import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ex_04_03_LevelList {
    static class Node {
        final int data;
        Node left;
        Node right;
        Node(int data) { this.data = data; }
    }

    static Node addAll(int[] array, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        Node node = new Node(array[mid]);
        node.left = addAll(array, start, mid - 1);
        node.right = addAll(array, mid + 1, end);
        return node;
    }

    static Node createMinimalBst(int[] array) {
        return addAll(array, 0, array.length - 1);
    }

    static ArrayList<LinkedList<Node>> linkLevelsUsingBfs(Node root) {
        ArrayList<LinkedList<Node>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            LinkedList<Node> list = new LinkedList<>();
            for (int i = 0; i < count; ++i) {
                Node u = queue.poll();
                list.add(u);
                if (u.left != null) queue.add(u.left);
                if (u.right != null) queue.add(u.right);
            }
            result.add(list);
        }
        return result;
    }

    static ArrayList<LinkedList<Node>> linkLevelsUsingBfs2(Node root) {
        ArrayList<LinkedList<Node>> result = new ArrayList<>();
        if (root == null) return result;

        LinkedList<Node> current = new LinkedList<>();
        current.add(root);

        while (!current.isEmpty()) {
            result.add(current);
            LinkedList<Node> parents = current;
            current = new LinkedList<>();
            for (Node parent : parents) {
                if (parent.left != null) current.add(parent.left);
                if (parent.right != null) current.add(parent.right);
            }
        }

        return result;
    }

    static void linkLevelsUsingDfs(Node node, ArrayList<LinkedList<Node>> result, int level) {
        if (node == null) {
            return;
        }

        LinkedList<Node> list;
        if (result.size() == level) {
            list = new LinkedList<>();
            result.add(list);
        } else {
            list = result.get(level);
        }
        list.add(node);

        linkLevelsUsingDfs(node.left, result, level + 1);
        linkLevelsUsingDfs(node.right, result, level + 1);
    }

    static ArrayList<LinkedList<Node>> linkLevelsUsingDfs(Node root) {
        ArrayList<LinkedList<Node>> result = new ArrayList<>();
        linkLevelsUsingDfs(root, result, 0);
        return result;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Node tree = createMinimalBst(array);

        System.out.println("DFS");
        {
            ArrayList<LinkedList<Node>> levels = linkLevelsUsingDfs(tree);
            for (LinkedList<Node> level : levels) {
                for (Node each : level) {
                    System.out.printf("%d ", each.data);
                }
                System.out.println();
            }
        }

        System.out.println("BFS");
        {
            ArrayList<LinkedList<Node>> levels = linkLevelsUsingBfs2(tree);
            for (LinkedList<Node> level : levels) {
                for (Node each : level) {
                    System.out.printf("%d ", each.data);
                }
                System.out.println();
            }
        }

    }
}
