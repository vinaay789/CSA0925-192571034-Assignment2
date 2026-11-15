import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DesignChallenge {
    static class OriginalUpNext {
        private final List<String> songs = new ArrayList<>();

        void addAtEnd(String song) {
            songs.add(song);
        }

        void addAtFront(String song) {
            songs.add(0, song);
        }

        void removeFront() {
            if (!songs.isEmpty()) {
                songs.remove(0);
            }
        }

        long benchmarkFrontHeavy(int n) {
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                addAtFront("song-" + i);
                removeFront();
            }
            return System.nanoTime() - start;
        }
    }

    static class RedesignedUpNext {
        private final Deque<String> songs = new ArrayDeque<>();

        void addAtEnd(String song) {
            songs.offerLast(song);
        }

        void addAtFront(String song) {
            songs.offerFirst(song);
        }

        void removeFront() {
            if (!songs.isEmpty()) {
                songs.pollFirst();
            }
        }

        long benchmarkFrontHeavy(int n) {
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                addAtFront("song-" + i);
                removeFront();
            }
            return System.nanoTime() - start;
        }
    }

    public static void main(String[] args) {
        System.out.println("Q7 – Diagnosis and redesign challenge");
        System.out.println("Diagnosis: the original list-based implementation repeatedly shifts elements when adding/removing at the front, so the work grows linearly with the queue size.");
        System.out.println("Redesign: ArrayDeque supports O(1) insertion/removal at both ends, so the front-heavy queue stays efficient as demand grows.");

        OriginalUpNext original = new OriginalUpNext();
        RedesignedUpNext redesigned = new RedesignedUpNext();

        int[] sizes = {1000, 10000, 100000};
        for (int n : sizes) {
            long originalTime = original.benchmarkFrontHeavy(n);
            long redesignedTime = redesigned.benchmarkFrontHeavy(n);
            System.out.println("n=" + n + " => original=" + originalTime + " ns, redesigned=" + redesignedTime + " ns");
        }

        System.out.println("Conclusion: the redesign meets its objective by avoiding repeated shifting and reducing the dominant front operation from O(n) to O(1). The trade-off is that ArrayDeque is not an indexable list and is optimised for queue/deque usage rather than arbitrary insertion in the middle.");
    }
}
