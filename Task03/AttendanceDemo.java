import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class AttendanceDemo {
    static class BrokenStudent {
        final String regNo;
        final String name;

        BrokenStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BrokenStudent)) return false;
            return regNo.equals(((BrokenStudent) o).regNo);
        }

        @Override
        public String toString() {
            return regNo + " " + name;
        }
    }

    static class Student {
        private final String regNo;
        private final String name;

        Student(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            return regNo.equals(((Student) o).regNo);
        }

        @Override
        public int hashCode() {
            return regNo.hashCode();
        }

        @Override
        public String toString() {
            return regNo + " " + name;
        }
    }

    static void testSetSizePrediction(String label, Set<?> set, int predictedSize, int actualSize) {
        System.out.println(label + " -> predicted size=" + predictedSize + ", actual size=" + actualSize);
    }

    public static void main(String[] args) {
        System.out.println("Q3 – hashCode–equals contract investigation");

        Set<BrokenStudent> broken = new HashSet<>();
        broken.add(new BrokenStudent("192311001", "Arun"));
        broken.add(new BrokenStudent("192311002", "Divya"));
        broken.add(new BrokenStudent("192311001", "Arun"));
        System.out.println("BrokenStudent HashSet size: " + broken.size());
        System.out.println("Reason: equals() says same regNo is equal, but hashCode() is not overridden, so HashSet can place equal elements in different buckets.");
        System.out.println();

        Set<Student> good = new HashSet<>();
        good.add(new Student("192311001", "Arun"));
        good.add(new Student("192311002", "Divya"));
        good.add(new Student("192311001", "Arun"));
        System.out.println("Fixed Student HashSet size: " + good.size());
        System.out.println("Reason: same regNo means same hash and equals, so duplicate is suppressed." );
        System.out.println();

        Set<Student> beforeMutation = new HashSet<>();
        Student s = new Student("192311003", "Nisha");
        beforeMutation.add(s);
        System.out.println("Before mutation: " + beforeMutation.size());
        s = new Student("192311004", "Nisha");
        System.out.println("Mutation note: if a field used in hashCode() changes after insertion, the object can become unreachable from its bucket and lookups fail.");

        TreeSet<Student> tree = new TreeSet<>((a, b) -> a.regNo.compareTo(b.regNo));
        System.out.println("TreeSet with regNo comparator yields sorted order by regNo.");
        tree.add(new Student("192311001", "Arun"));
        tree.add(new Student("192311002", "Divya"));
        tree.add(new Student("192311001", "Arun"));
        System.out.println("TreeSet size: " + tree.size());

        testSetSizePrediction("Test 1: duplicate scans", good, 2, good.size());
        testSetSizePrediction("Test 2: broken hash contract", broken, 3, broken.size());
        System.out.println("Conclusion: hashCode() must be consistent with equals() and must use unchanged fields.");
    }
}
