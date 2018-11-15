package queue;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int numberOfStrings = Integer.parseInt(args[0]);

        for (int i = 0; i < numberOfStrings; i++) {
            queue.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
