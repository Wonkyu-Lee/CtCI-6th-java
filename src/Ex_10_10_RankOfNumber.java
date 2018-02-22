public class Ex_10_10_RankOfNumber {

    Node root;

    static class Node {
        int value;
        int leftSize;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    Node addValue(Node node, int x) {
        if (node == null) {
            return new Node(x);
        }

        if (x <= node.value) {
            node.left = addValue(node.left, x);
            node.leftSize++;
        } else {
            node.right = addValue(node.right, x);
        }

        return node;
    }

    int getRank(Node node, int x) {
        if (node == null) {
            return -1;
        }

        if (x == node.value) {
            return node.leftSize;
        } else if (x < node.value) {
            return getRank(node.left, x);
        } else {
            int rank = getRank(node.right, x);
            return (rank == -1) ? -1 : (node.leftSize + 1 + rank);
        }
    }

    void track(int x) {
        root = addValue(root, x);
    }

    int getRankOfNumber(int x) {
        return getRank(root, x);
    }

    Ex_10_10_RankOfNumber() {
        int[] array = {5, 1, 4, 4, 5, 9, 7, 13, 3};
        for (int i : array) {
            track(i);
        }

        System.out.println("Rank of 1: " + getRankOfNumber(1));
        System.out.println("Rank of 3: " + getRankOfNumber(3));
        System.out.println("Rank of 4: " + getRankOfNumber(4));
    }

    public static void main(String[] args) {
        new Ex_10_10_RankOfNumber();
    }
}
