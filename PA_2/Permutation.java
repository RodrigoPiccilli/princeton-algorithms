import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int printQuantity = Integer.parseInt(args[0]);

        int kCount = 0;

        RandomizedQueue<String> random = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {

            if (kCount < printQuantity) {

                String current = StdIn.readString();

                random.enqueue(current);

                kCount++;

            } else {

                String current = StdIn.readString();

                random.dequeue();

                random.enqueue(current);

            }

        }

        for (int i = 0; i < printQuantity; i++) {
            System.out.println(random.dequeue());
        }

    }
}
