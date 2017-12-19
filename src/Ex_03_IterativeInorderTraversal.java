import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Ex_03_IterativeInorderTraversal {

    class Node {
        Node left;
        Node right;
        int value;
        Node(int value) {
            this.value = value;
        }
    }

    Node insert(Node node, int value) {
        if (node == null)
            return new Node(value);

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }

        return node;
    }

    interface Visitor {
        void visit(Node node);
    }

    void inorderTraverse(Node node, Visitor visitor) {
        if (node == null)
            return;

        Stack<Node> stack = new Stack<>();
        boolean done = false;

        Node current = node;
        while (!done) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                if (!stack.isEmpty()) {
                    current = stack.pop();
                    visitor.visit(current);
                    current = current.right;
                } else {
                    done = true;
                }
            }
        }
    }

    void inorderTraverse2(Node node, Visitor visitor) {
        if (node == null)
            return;

        inorderTraverse2(node.left, visitor);
        visitor.visit(node);
        inorderTraverse2(node.right, visitor);
    }

    void breadthFirstTraverse(Node node, Visitor visitor) {
        if (node == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visitor.visit(current);

            if (current.left != null)
                queue.offer(current.left);

            if (current.right != null)
                queue.offer(current.right);
        }
    }


    enum DfsType { PREORDER, INORDER, POSTORDER }
    void depthFirstTraverse(Node node, Visitor visitor, DfsType type) {
        if (type == DfsType.POSTORDER) {
            // TODO:
            throw new UnsupportedOperationException();
        }

        boolean done = false;
        Stack<Node> stack = new Stack<>();
        Node current = node;

        while (!done) {
            if (current != null) {
                if (type == DfsType.PREORDER) visitor.visit(current);
                stack.push(current);
                current = current.left;
            } else {
                if (!stack.isEmpty()) {
                    current = stack.pop();
                    if (type == DfsType.INORDER) visitor.visit(current);
                    current = current.right;
                } else {
                    done = true;
                }
            }
        }
    }

    Ex_03_IterativeInorderTraversal() {
        Node tree = null;
        tree = insert(tree, 5);
        insert(tree, 3);
        insert(tree, 8);
        insert(tree, 1);
        insert(tree, 4);
        insert(tree, 9);
        insert(tree, 7);
        insert(tree, 2);
        insert(tree, 6);

        depthFirstTraverse(tree, (Node node)->{
            System.out.printf("%d ", node.value);
        }, DfsType.PREORDER);
        System.out.println();

        breadthFirstTraverse(tree, (Node node)->{
            System.out.printf("%d ", node.value);
        });
        System.out.println();
    }

    public static void main(String[] args) {
        new Ex_03_IterativeInorderTraversal();
    }
}
