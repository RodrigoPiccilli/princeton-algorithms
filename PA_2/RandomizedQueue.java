import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int capacity;
    private Item[] list;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        capacity = 10;
        list = (Item[]) new Object[capacity];
    }

    private void resize() {

        if (size == capacity) {

            capacity = capacity * 2;

            Item[] newList = (Item[]) new Object[capacity];

            int count = 0;

            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {

                    newList[count++] = list[i];

                }
            }

            list = newList;

        }

        if (size == (0.25 * (double) capacity)) {

            capacity = capacity / 2;

            Item[] newList = (Item[]) new Object[capacity];

            int count = 0;

            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {

                    newList[count++] = list[i];

                }
            }

            list = newList;

        }

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        list[size++] = item;

        resize();

    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniformInt(size);

        Item value = list[randomIndex];

        list[randomIndex] = list[size - 1];

        list[size - 1] = null;

        size--;

        resize();

        return value;

    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniformInt(size);

        return list[randomIndex];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {

        private int count;
        private Item[] iteratorList;

        public RQIterator() {
            count = size;

            iteratorList = (Item[]) new Object[size];

            int copyCounter = 0;

            for (int i = 0; i < list.length; i++) {

                if (list[i] != null) {
                    iteratorList[copyCounter++] = list[i];
                }

            }

            StdRandom.shuffle(iteratorList);
        }

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return iteratorList[--count];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        System.out.println("Verify Queue is Empty: " + rq.isEmpty());

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        Iterator<Integer> it = rq.iterator();

        System.out.println("Verify Size: " + rq.size());

        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());

        System.out.println("Verify Size: " + rq.size());

        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());

        System.out.println("Verify Size: " + rq.size());

        it = rq.iterator();

        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }

}
