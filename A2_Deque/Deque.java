import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node previous;
        private Node next;
    }

    private Node first;
    private Node last;
    private int n;

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return n == 0;
    }

    public int size() {
        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        }
        Node newNode = new Node();
        newNode.item = item;

        if (isEmpty()) {
            newNode.previous = null;
            newNode.next = null;
            first = newNode;
            last = newNode;
        } else {
            newNode.previous = null;
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        }
        n++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        }

        Node newNode = new Node();
        newNode.item = item;

        if (isEmpty()) {
            newNode.previous = null;
            newNode.next = null;
            first = newNode;
            last = newNode;
        } else {
            newNode.previous = last;
            newNode.next = null;
            last.next = newNode;
            last = newNode;
        }
        n++;

    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (first == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }
        n--;
        return item;
    }

    public Item removeLast() {
        // remove and return the item from the last
        if (first == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        Item item = last.item;
        last = last.previous;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        n--;
        return item;

    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /*
     * public static void main(String[] args) { Deque<String> queue = new
     * Deque<String>(); System.out.println(queue.isEmpty());
     * System.out.println(queue.size()); queue.addLast("b");
     * queue.addFirst("a"); queue.addLast("c"); queue.addLast("d");
     * queue.addFirst("e"); queue.removeFirst(); queue.removeFirst();
     * queue.removeLast(); queue.removeLast();
     * 
     * for (String s: queue){ System.out.println(s); } }
     */

}
