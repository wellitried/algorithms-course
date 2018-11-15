package queue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int head;
    private int tail;
    private int size;


    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        head = 0;
        tail = 0;
        size = 0;
    }


    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (!isEmpty()) {
            tail++;
        }

        resizeArrayIfNecessary();

        array[tail] = item;

        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = getRandomIndex();

        Item item = array[randomIndex];

        replaceRandomIndexWithTail(randomIndex);
        size--;

        if (isEmpty()) {
            head = 0;
            tail = 0;
        }

        return item;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = getRandomIndex();

        return array[randomIndex];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }


    private void replaceRandomIndexWithTail(int randomIndex) {
        array[randomIndex] = array[tail];
        array[tail] = null;
        tail--;
    }

    private int getRandomIndex() {
        return tail == 0 ? 0 : (StdRandom.uniform(tail) + head);
    }

    private void resizeArrayIfNecessary() {
        if (tail == array.length) {
            resize(2 * array.length);
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }

        array = copy;
    }


    private class ArrayIterator implements Iterator<Item> {

        public boolean hasNext() {
            return !isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return dequeue();
        }
    }
}
