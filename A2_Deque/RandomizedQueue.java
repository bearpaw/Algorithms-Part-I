import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q; // queue elements
    private int n; // number of elements on queue
    private int first; // index of first element of queue
    private int last; // index of next available slot

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    /**
     * Is this queue empty?
     * 
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in this queue.
     * 
     * @return the number of items in this queue
     */
    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    /**
     * Adds the item to this queue.
     * 
     * @param item
     *            the item to add
     */
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (item == null) {
            throw new NullPointerException();
        }
        if (n == q.length)
            resize(2 * q.length); // double size of array if necessary
        q[last++] = item; // add item
        if (last == q.length)
            last = 0; // wrap-around
        n++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     * 
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException
     *             if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int index = StdRandom.uniform(n);

        Item item = q[index];
        if (index == first) { // remove the first item
            first++;
            if (first == q.length)
                first = 0; // wrap-around
            // shrink size of array if necessary
            if (n > 0 && n == q.length / 4)
                resize(q.length / 2);
        } else if (index == last - 1) { // remove the last item
            if (last == 0) {
                last = n-1;
            } else {
                last--;
            }
        } else {
            for (int i = index; i < n - 1; ++i) { // remove the middle item
                q[i] = q[i + 1];
            }
            if (last == 0) {
                last = n-1;
            } else {
                last--;
            }
        }
        n--;
        if (n == 0) {
            first = 0;
            last = 0;
        }
        return item;
    }

    /**
     * Returns the item least recently added to this queue.
     * 
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException
     *             if this queue is empty
     */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        return q[StdRandom.uniform(n)];
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO
     * order.
     * 
     * @return an iterator that iterates over the items in this queue in FIFO
     *         order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] perm;

        public ArrayIterator() {
            perm = new int[n];
            for (int arrI = 0; arrI < n; ++arrI) {
                perm[arrI] = arrI;
            }
            StdRandom.shuffle(perm);
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = q[perm[i]];
            i++;
            return item;
        }
    }
    
/*    public void print_status() {
        System.out.format("Length: %d | n: %d | first: %d | last: %d\n", q.length, n, first, last);
    }
    
     public static void main(String[] args) {
         RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
         int a;
         rq.print_status();
         rq.enqueue(4);rq.print_status();
         rq.enqueue(30);rq.print_status();
         rq.enqueue(31);rq.print_status();
         rq.isEmpty()   ; rq.print_status(); //==> false
         rq.enqueue(9);rq.print_status();
         rq.dequeue()   ; rq.print_status();// ==> 9
         rq.enqueue(23);rq.print_status();
     }*/

}