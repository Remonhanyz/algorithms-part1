// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int tail = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < tail; i++)
            copy[i] = arr[i];
        arr = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (tail == 0)
            return true;
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (tail == arr.length)
            resize(arr.length * 2);
        arr[tail++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (tail == 0)
            throw new java.util.NoSuchElementException();
        int num = StdRandom.uniform(0, tail);
        Item temp = arr[num++];

        for (; num - 1 < tail - 1; num++) {
            arr[num - 1] = arr[num];
        }
        arr[num - 1] = null;
        tail--;
        if (tail > 0 && tail == arr.length / 4)
            resize(arr.length / 2);
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (tail == 0)
            throw new java.util.NoSuchElementException();
        int num = StdRandom.uniform(0, tail);
        return arr[num];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] array = arr.clone();
        private int length = tail;
        private int current = StdRandom.uniform(0, length);

        public boolean hasNext() {
            return length > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (array[current] == null)
                throw new java.util.NoSuchElementException();
            Item temp = array[current];
            int i;
            for (i = current; i < tail - 1; i++) {

                array[i] = array[i + 1];
            }
            array[i] = null;
            if (length != 1)
                current = StdRandom.uniform(0, --length);
            else
                --length;
            return temp;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        // // Iterator<Integer> iterator = queue.iterator();
        // while (iterator.hasNext())
        //     StdOut.println(iterator.next());
        // queue.enqueue('a');
        // queue.enqueue('b');
        // queue.enqueue('c');
        // StdOut.println(queue.dequeue());
        // queue.dequeue();
    }
}
