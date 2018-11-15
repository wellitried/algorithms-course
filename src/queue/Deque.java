package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;


    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }


    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            initEmptyList(item);
        } else {
            Node newNode = new Node();
            newNode.item = item;
            newNode.next = first;

            first.previous = newNode;

            first = newNode;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            initEmptyList(item);
        } else {
            Node newNode = new Node();
            newNode.item = item;
            newNode.previous = last;

            last.next = newNode;

            last = newNode;
        }


        size++;
    }

    private void initEmptyList(Item item) {
        Node node = new Node();
        node.item = item;

        first = node;
        last = node;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        first = first.next;
        if (first != null) {
            first.previous = null;
        }

        decreaseSize();

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        last = last.previous;
        if (last != null) {
            last.next = null;
        }

        decreaseSize();

        return item;
    }

    private void decreaseSize() {

        size--;

        if (isEmpty()) {
            first = null;
            last = null;
        }
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }
}
