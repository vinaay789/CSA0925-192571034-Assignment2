import java.util.ArrayList;
import java.util.List;

public class StringBuilderExperiment {
    private static final int[] RUNS = {5, 10};

    static String[] buildFields(int count) {
        String[] fields = new String[count];
        for (int i = 0; i < count; i++) {
            fields[i] = "F" + i + "=" + (i * 17 + 3);
        }
        return fields;
    }

    static long buildWithString(int tickets, int fieldsPerTicket) {
        String[] fields = buildFields(fieldsPerTicket);
        long start = System.nanoTime();
        for (int t = 0; t < tickets; t++) {
            String result = "";
            for (String field : fields) {
                result += field + " | ";
            }
            if (t == tickets - 1) {
                // Force use of result to keep the compiler from optimising the loop away.
                if (result.length() < 0) {
                    throw new IllegalStateException();
                }
            }
        }
        return System.nanoTime() - start;
    }

    static long buildWithStringBuilder(int tickets, int fieldsPerTicket) {
        String[] fields = buildFields(fieldsPerTicket);
        long start = System.nanoTime();
        for (int t = 0; t < tickets; t++) {
            StringBuilder result = new StringBuilder();
            for (String field : fields) {
                result.append(field).append(" | ");
            }
            if (t == tickets - 1) {
                if (result.length() < 0) {
                    throw new IllegalStateException();
                }
            }
        }
        return System.nanoTime() - start;
    }

    static long buildWithStringBuffer(int tickets, int fieldsPerTicket) {
        String[] fields = buildFields(fieldsPerTicket);
        long start = System.nanoTime();
        for (int t = 0; t < tickets; t++) {
            StringBuffer result = new StringBuffer();
            for (String field : fields) {
                result.append(field).append(" | ");
            }
            if (t == tickets - 1) {
                if (result.length() < 0) {
                    throw new IllegalStateException();
                }
            }
        }
        return System.nanoTime() - start;
    }

    static long average(long[] values) {
        long total = 0;
        for (long value : values) {
            total += value;
        }
        return total / values.length;
    }

    static void benchmark(int tickets, int fields) {
        List<String> report = new ArrayList<>();
        long[] strTimes = new long[RUNS.length];
        long[] sbTimes = new long[RUNS.length];
        long[] sbfTimes = new long[RUNS.length];

        for (int i = 0; i < RUNS.length; i++) {
            strTimes[i] = buildWithString(tickets, fields);
            sbTimes[i] = buildWithStringBuilder(tickets, fields);
            sbfTimes[i] = buildWithStringBuffer(tickets, fields);
        }

        long avgString = average(strTimes);
        long avgBuilder = average(sbTimes);
        long avgBuffer = average(sbfTimes);

        long stringObjects = (long) tickets * (fields - 1);
        long builderObjects = tickets;
        long bufferObjects = tickets;

        report.add("Workload: tickets=" + tickets + ", fields=" + fields);
        report.add("String average time: " + avgString + " ns");
        report.add("StringBuilder average time: " + avgBuilder + " ns");
        report.add("StringBuffer average time: " + avgBuffer + " ns");
        report.add("Estimated intermediate String objects: String=" + stringObjects + ", StringBuilder=" + builderObjects + ", StringBuffer=" + bufferObjects);
        report.add("Prediction: String is worst because each + creates a new String; StringBuilder/StringBuffer are linear and better.");
        report.add("Observation: StringBuilder usually wins for single-threaded ticket creation; StringBuffer is safe in shared multi-threaded logs, but slower due to synchronization.");
        report.add("---");
        for (String line : report) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        System.out.println("Q1 – Ticket generation benchmark");
        System.out.println("Predictive reasoning: String is immutable, so concatenation with + creates a new String each time. StringBuilder mutates a single buffer and StringBuffer does the same with synchronized methods.");
        System.out.println();

        benchmark(10, 10);
        benchmark(10000, 50);
        benchmark(20000, 80);

        System.out.println(
            "Recommendation: use StringBuilder for per-ticket text generation and StringBuffer only when several threads share the same log and synchronization is required."
        );
    }
}
