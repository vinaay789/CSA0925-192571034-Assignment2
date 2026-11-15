import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PharmacyInventoryDemo {
    static class Medicine {
        final String code;
        final String name;
        final int stock;

        Medicine(String code, String name, int stock) {
            this.code = code;
            this.name = name;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return code + "=" + stock;
        }
    }

    static long timeLookups(Map<String, Integer> map, int lookups) {
        long start = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            map.get("MED" + (i % 1000));
        }
        return System.nanoTime() - start;
    }

    static long timeReport(Map<String, Integer> map) {
        long start = System.nanoTime();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            // make sure iteration is performed
            if (e.getValue() < 0) {
                throw new IllegalStateException();
            }
        }
        return System.nanoTime() - start;
    }

    static Map<String, Integer> buildMap(int size, Map<String, Integer> mapType) {
        for (int i = 0; i < size; i++) {
            mapType.put("MED" + i, 10 + (i % 25));
        }
        return mapType;
    }

    static void runForSize(int n) {
        List<Medicine> inventory = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            inventory.add(new Medicine("MED" + i, "Drug" + i, 10 + (i % 25)));
        }

        Map<String, Integer> hashMap = buildMap(n, new HashMap<>());
        Map<String, Integer> treeMap = buildMap(n, new TreeMap<>());
        Map<String, Integer> linkedHashMap = buildMap(n, new LinkedHashMap<>());

        long hashLookup = timeLookups(hashMap, 100000);
        long treeLookup = timeLookups(treeMap, 100000);
        long linkedLookup = timeLookups(linkedHashMap, 100000);

        long hashReport = timeReport(hashMap);
        long treeReport = timeReport(treeMap);
        long linkedReport = timeReport(linkedHashMap);

        System.out.println("Inventory size: " + n);
        System.out.println("HashMap lookup (100000): " + hashLookup + " ns");
        System.out.println("TreeMap lookup (100000): " + treeLookup + " ns");
        System.out.println("LinkedHashMap lookup (100000): " + linkedLookup + " ns");
        System.out.println("HashMap report iteration: " + hashReport + " ns");
        System.out.println("TreeMap code-order report: " + treeReport + " ns");
        System.out.println("LinkedHashMap insertion-order report: " + linkedReport + " ns");
        System.out.println("Result: TreeMap naturally gives code order, LinkedHashMap naturally gives insertion order, HashMap is unordered but fastest average lookup.");
        System.out.println("---");
    }

    public static void main(String[] args) {
        System.out.println("Q4 – Pharmacy inventory map comparison");
        System.out.println("Expected behaviour: HashMap gives O(1) average get/put, TreeMap gives O(log n) but sorted order, LinkedHashMap adds insertion order with O(1) average access.");
        runForSize(1000);
        runForSize(10000);
        runForSize(100000);
        System.out.println("Conclusion: deploy TreeMap when code order is required for the nightly report, else HashMap is the best general-purpose choice for fast repeated lookups.");
    }
}
