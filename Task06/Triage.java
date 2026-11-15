import java.util.Comparator;
import java.util.PriorityQueue;

class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {
    private final PriorityQueue<Patient> queue;
    private long counter = 0;

    Triage() {
        queue = new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.severity)
                .thenComparingLong(p -> p.arrival)
        );
    }

    void admit(String name, int severity) {
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    public static void main(String[] args) {
        System.out.println("Q6 – Emergency triage with PriorityQueue");
        Triage er = new Triage();
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);

        StringBuilder order = new StringBuilder();
        Patient p;
        while ((p = er.next()) != null) {
            order.append(p).append(' ');
        }

        System.out.println(order.toString().trim());
        System.out.println("Explanation: the arrival counter makes equal-severity patients come out in their original arrival order, because the comparator uses severity first and arrival second.");
        System.out.println("Note: printing the queue directly is not the treatment order; PriorityQueue is not a FIFO queue but a heap.");
    }
}
