import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node head;
    private Node tail;

    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
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

            head = new Node(item, null, null);

            tail = head;

        } else {

            Node oldHead = head;

            Node newHead = new Node(item, oldHead, null);

            oldHead.prev = newHead;

            head = newHead;

        }

        size++;

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {

            tail = new Node(item, null, null);

            head = tail;

        } else {

            Node oldTail = tail;

            Node newTail = new Node(item, null, oldTail);

            oldTail.next = newTail;

            tail = newTail;

        }

        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item value = head.value;

        if (size == 1) {

            head = null;

            tail = null;

        } else {

            head = head.next;

            head.prev = null;

        }

        size--;

        return value;

    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item value = tail.value;

        if (size == 1) {

            tail = null;

            head = null;

        } else {

            tail = tail.prev;

            tail.next = null;

        }

        size--;

        return value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current;

        public DequeIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item value = current.value;

            current = current.next;

            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");

        }

    }

    private class Node {

        private Item value;
        private Node next;
        private Node prev;

        public Node(Item value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();
        Iterator<Integer> it = deque.iterator();

        System.out.println(it.hasNext());

        System.out.println("Verify Queue is Empty: " + deque.isEmpty());

        deque.addFirst(4);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        System.out.println("Verify Size: " + deque.size());

        for (int i : deque) {
            System.out.print(i + " ");
        }

        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);

        System.out.println("\nVerify Size: " + deque.size());

        for (int i : deque) {
            System.out.print(i + " ");
        }

        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();

        System.out.println("\nVerify Size: " + deque.size());

        for (int i : deque) {
            System.out.print(i + " ");
        }

        deque.removeLast();
        deque.removeLast();
        deque.removeLast();

        System.out.println("\nVerify Size: " + deque.size());

        for (int i : deque) {
            System.out.print(i + " ");
        }

    }

}