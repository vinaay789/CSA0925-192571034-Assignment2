import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PlaylistBenchmark {
    private static final int[] SIZES = {1000, 10000, 100000};

    static class Song {
        final String title;
        Song(String title) {
            this.title = title;
        }
    }

    static List<Song> makeList(int size) {
        List<Song> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Song("Song-" + i));
        }
        return list;
    }

    static long timeAddAtEnd(List<Song> list, int adds) {
        long start = System.nanoTime();
        for (int i = 0; i < adds; i++) {
            list.add(new Song("added-" + i));
        }
        return System.nanoTime() - start;
    }

    static long timeInsertFront(List<Song> list, int inserts) {
        long start = System.nanoTime();
        for (int i = 0; i < inserts; i++) {
            list.add(0, new Song("front-" + i));
        }
        return System.nanoTime() - start;
    }

    static long timeInsertMiddle(List<Song> list, int inserts) {
        long start = System.nanoTime();
        int mid = list.size() / 2;
        for (int i = 0; i < inserts; i++) {
            list.add(mid + i, new Song("middle-" + i));
        }
        return System.nanoTime() - start;
    }

    static long timeRemoveFront(List<Song> list, int removals) {
        long start = System.nanoTime();
        for (int i = 0; i < removals; i++) {
            if (!list.isEmpty()) {
                list.remove(0);
            }
        }
        return System.nanoTime() - start;
    }

    static long timeGetKth(List<Song> list, int k) {
        long start = System.nanoTime();
        for (int i = 0; i < 200; i++) {
            Song s = list.get(k % list.size());
            if (s == null) {
                throw new IllegalStateException();
            }
        }
        return System.nanoTime() - start;
    }

    static long timeIterate(List<Song> list) {
        long start = System.nanoTime();
        long total = 0;
        for (Song s : list) {
            total += s.title.length();
        }
        return System.nanoTime() - start;
    }

    static long timeArrayDequeFrontHeavy(int size, int operations) {
        Deque<Song> queue = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            queue.offerFirst(new Song("queue-" + i));
        }
        long start = System.nanoTime();
        for (int i = 0; i < operations; i++) {
            queue.offerFirst(new Song("new-" + i));
            queue.pollFirst();
        }
        return System.nanoTime() - start;
    }

    static void printTable(String label, long[] values) {
        System.out.println(label + ": " + java.util.Arrays.toString(values));
    }

    static void benchmarkForSize(int size) {
        List<Song> arrayList = makeList(size);
        List<Song> linkedList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            linkedList.add(new Song("Song-" + i));
        }

        System.out.println("\nPlaylist size: " + size);
        long[] addEndArray = {timeAddAtEnd(arrayList, 200), timeAddAtEnd(arrayList, 200), timeAddAtEnd(arrayList, 200)};
        long[] addEndLinked = {timeAddAtEnd(linkedList, 200), timeAddAtEnd(linkedList, 200), timeAddAtEnd(linkedList, 200)};

        long[] insertFrontArray = {timeInsertFront(arrayList, 200), timeInsertFront(arrayList, 200), timeInsertFront(arrayList, 200)};
        long[] insertFrontLinked = {timeInsertFront(linkedList, 200), timeInsertFront(linkedList, 200), timeInsertFront(linkedList, 200)};

        long[] insertMiddleArray = {timeInsertMiddle(arrayList, 50), timeInsertMiddle(arrayList, 50), timeInsertMiddle(arrayList, 50)};
        long[] insertMiddleLinked = {timeInsertMiddle(linkedList, 50), timeInsertMiddle(linkedList, 50), timeInsertMiddle(linkedList, 50)};

        long[] getKthArray = {timeGetKth(arrayList, size / 2), timeGetKth(arrayList, size / 2), timeGetKth(arrayList, size / 2)};
        long[] getKthLinked = {timeGetKth(linkedList, size / 2), timeGetKth(linkedList, size / 2), timeGetKth(linkedList, size / 2)};

        long[] iterateArray = {timeIterate(arrayList), timeIterate(arrayList), timeIterate(arrayList)};
        long[] iterateLinked = {timeIterate(linkedList), timeIterate(linkedList), timeIterate(linkedList)};

        printTable("ArrayList add at end", addEndArray);
        printTable("LinkedList add at end", addEndLinked);
        printTable("ArrayList insert at front", insertFrontArray);
        printTable("LinkedList insert at front", insertFrontLinked);
        printTable("ArrayList insert in middle", insertMiddleArray);
        printTable("LinkedList insert in middle", insertMiddleLinked);
        printTable("ArrayList get k-th", getKthArray);
        printTable("LinkedList get k-th", getKthLinked);
        printTable("ArrayList iterate", iterateArray);
        printTable("LinkedList iterate", iterateLinked);

        long dequeTime = timeArrayDequeFrontHeavy(size, 200);
        System.out.println("ArrayDeque front-heavy queue (200 ops): " + dequeTime + " ns");
    }

    public static void main(String[] args) {
        System.out.println("Q2 – ArrayList, LinkedList and ArrayDeque benchmark");
        System.out.println("Engineering view: ArrayList is best for append-heavy workloads and direct indexing; LinkedList is better only for frequent front/middle insertion; ArrayDeque is superior for front/back queue operations.");
        for (int size : SIZES) {
            benchmarkForSize(size);
        }
    }
}
