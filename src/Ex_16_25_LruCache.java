import java.util.HashMap;

public class Ex_16_25_LruCache {

    public static class Cache<K, T> {
        private final HashMap<K, Node> mMap = new HashMap<>();
        private final int mCapacity;
        private Node mHead;
        private Node mTail;

        private class Node {
            K key;
            T value;
            Node prev;
            Node next;

            Node(K key, T value) {
                this.key = key;
                this.value = value;
            }
        }

        public Cache(int capacity) {
            mCapacity = capacity;
        }

        public T get(K key) {
            Node node = mMap.get(key);
            if (node == null) {
                return null;
            }

            if (node != mHead) {
                removeFromList(node);
                addFirstOfList(node);
            }

            return node.value;
        }

        public void put(K key, T value) {
            remove(key);

            if (mCapacity == mMap.size() && mTail != null) {
                remove(mTail.key);
            }

            Node node = new Node(key, value);
            mMap.put(key, node);
            addFirstOfList(node);
        }

        public boolean remove(K key) {
            Node node = mMap.get(key);
            if (node == null) {
                return false;
            }

            removeFromList(node);
            mMap.remove(node);
            return true;
        }

        private void removeFromList(Node node) {
            if (node == null)
                return;

            if (mHead == node) {
                mHead = node.next;
            }

            if (mTail == node) {
                mTail = node.prev;
            }

            if (node.prev != null) {
                node.prev.next = node.next;
            }

            if (node.next != null) {
                node.next.prev = node.prev;
            }

            node.prev = null;
            node.next = null;
        }

        private void addFirstOfList(Node node) {
            if (mHead == null) {
                mHead = node;
                mTail = node;
                return;
            }

            mHead.prev = node;
            node.next = mHead;
            mHead = node;
        }

        public void debug() {
            System.out.printf("[head:%d, tail: %d]", (mHead != null ? mHead.key : "-1"), (mTail != null ? mTail.key : "-1"));

            Node node = mHead;
            while (node != null) {
                System.out.print(node.value + " ");
                node = node.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Cache<Integer, String> cache = new Cache<>(3);
        cache.put(1, "Text1");
        cache.debug();
        cache.put(2, "Text2");
        cache.debug();
        cache.put(3, "Text3");
        cache.debug();
        cache.put(4, "Text4");
        cache.debug();
        cache.put(5, "Text5");
        cache.debug();
        cache.get(3);
        cache.debug();
        cache.put(5, "Text5");
        cache.debug();
        cache.put(6, "Text6");
        cache.debug();
    }
}
