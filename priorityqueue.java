
public class priorityqueue<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;

    public priorityqueue(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        return n;
    }

    public void insert(Key key) {
        pq[++n] = key;
        swim(n);
    }

    public Key delMin() {
        Key min = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;
        return min;
    }
    public Key peek() {
        if (isEmpty()) {
            return null;
        }
        return pq[1];
    }



    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);       // If the parent is larger, swap
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


    private boolean greater(int i, int j) {

        return pq[i].compareTo(pq[j]) > 0;
    }


    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
}




