public class Ex_04_04_CheckBalanced {

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

    static boolean isBalanced(Node node) {
        Result r = isBalancedRecurse(node);
        return r.balanced;
    }

    static class Result {
        boolean balanced;
        int height;
        Result(boolean balanced, int height) { this.balanced = balanced; this.height = height; }
    }

    static Result isBalancedRecurse(Node node) {
        if (node == null) {
            return new Result(true, 0);
        }

        Result resultL = isBalancedRecurse(node.left);
        if (!resultL.balanced) return resultL;

        Result resultR = isBalancedRecurse(node.right);
        if (!resultR.balanced) return resultR;

        if (Math.abs(resultL.height - resultR.height) > 1) {
            return new Result(false, -1);
        }

        int height = Math.max(resultL.height, resultR.height) + 1;
        return new Result(true, height);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Node tree = createMinimalBst(array);

        boolean balanced = isBalanced(tree);
        System.out.println("Result: " + balanced);
    }
}
