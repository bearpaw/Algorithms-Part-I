import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        Iterator<String> iter = queue.iterator();

        for (int i = 0; i < k; ++i) {
            System.out.println(iter.next());
        }

    }

}
