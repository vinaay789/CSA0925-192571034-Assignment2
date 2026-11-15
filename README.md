# CSA09 Assignment 2 Java Solutions

This repository contains Java source code for the seven tasks in the assignment, organised one folder per task.

## Project structure

- `Task01/` – string vs StringBuilder vs StringBuffer benchmarking
- `Task02/` – ArrayList vs LinkedList vs ArrayDeque benchmark study
- `Task03/` – hashCode–equals contract investigation and fix verification
- `Task04/` – HashMap vs TreeMap vs LinkedHashMap inventory comparison
- `Task05/` – generic ranking component built with Comparator and generic Leaderboard<T>
- `Task06/` – hospital triage simulation using PriorityQueue
- `Task07/` – diagnosis and redesign challenge using a front-heavy queue

## Java version

This project was tested with Java 17+ (`javac`/`java` default on the machine used for validation).

## Compile and run each task

```bash
javac Task01/StringBuilderExperiment.java
java -cp Task01 StringBuilderExperiment

javac Task02/PlaylistBenchmark.java
java -cp Task02 PlaylistBenchmark

javac Task03/AttendanceDemo.java
java -cp Task03 AttendanceDemo

javac Task04/PharmacyInventoryDemo.java
java -cp Task04 PharmacyInventoryDemo

javac Task05/LeaderboardDemo.java
java -cp Task05 LeaderboardDemo

javac Task06/Triage.java
java -cp Task06 Triage

javac Task07/DesignChallenge.java
java -cp Task07 DesignChallenge
```

## Notes

- The code includes predictions, measurements and explanations aligned to the assignment brief.
- Each program prints evidence suitable for a report and viva discussion.
- The experiments use repeated timings (`System.nanoTime()`) to show typical relative behaviour, rather than a single run.
