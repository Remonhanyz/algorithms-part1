import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

//using arrays
public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    // construct an empty deque
    public Deque() {
        arr = (Item[]) new Object[1];
    }

    private void resize(int capacity, boolean ishead) {
        Item[] copy = (Item[]) new Object[capacity];
        if (ishead) {
            for (int i = (capacity / 2), j = 0; j < tail; i++, j++) {
                copy[i] = arr[j];
            }
            int length = tail;
            arr = copy;
            tail = tail + (capacity / 2);
            if (head == -1 || (head == 0 && tail == 1))
                head = tail - length - 1;
            else if (head == 0)
                head = tail - length;
        } else {
            for (int i = 0; i < tail; i++) {
                copy[i] = arr[i];
            }
            arr = copy;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (head <= 0) {
            resize(2 * arr.length, true);
        }

        if (head == 0 && tail == 0)
            tail++;
        arr[head--] = item; // head
        count++;
    }

    // add the item to the back (stack)
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (tail == 0 && head == 0)
            tail = 1;
        if (tail == arr.length)
            resize(2 * arr.length, false);
        arr[tail++] = item; // tail
        count++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (tail == 0 && head == 0) throw new java.util.NoSuchElementException();

        Item item = arr[++head];
        arr[head] = null;
        count--;

        return item;
    }

    // remove and return the item from the back (stack)
    public Item removeLast() {
        if (tail == 0 && head == 0)
            throw new java.util.NoSuchElementException();
        Item item = arr[--tail];
        arr[tail] = null;
        if (tail > 0 && tail == arr.length / 4)
            resize(arr.length / 2, false);
        count--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = head;
        private int counter = 0;

        public boolean hasNext() {
            return counter != count;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (arr[1 + current] == null)
                throw new java.util.NoSuchElementException();
            counter++;
            return arr[++current];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.addFirst(2);
        // deque.removeFirst()
        // deque.removeFirst();
        StdOut.println(deque.removeLast());

        // StdOut.println(deque.removeFirst());
        // StdOut.println(deque.removeLast());

        // StdOut.println(deque.removeFirst());
        // deque.removeFirst();
        // deque.removeFirst();
        // StdOut.println(deque.removeFirst());

        // StdOut.println(deque.isEmpty());
    }
}