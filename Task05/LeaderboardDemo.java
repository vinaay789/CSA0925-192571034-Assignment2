import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Team {
    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {
    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    List<T> ranking() {
        List<T> copy = new ArrayList<>(items);
        copy.sort(order);
        return copy;
    }
}

public class LeaderboardDemo {
    public static void main(String[] args) {
        System.out.println("Q5 – Generic ranking design");

        Comparator<Team> byRank = Comparator
            .comparingInt((Team t) -> t.points)
            .reversed()
            .thenComparing(Comparator.comparingInt((Team t) -> t.goalDiff).reversed())
            .thenComparing(t -> t.name);

        Leaderboard<Team> board = new Leaderboard<>(byRank);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        System.out.println(board.ranking());
        System.out.println("Explanation: Bisons ranks above Panthers because they tie on points and goal difference, and then the name comparator breaks the tie alphabetically.");

        Comparator<Team> faulty = Comparator.comparingInt((Team t) -> t.points - t.goalDiff);
        List<Team> faultyList = new ArrayList<>();
        faultyList.add(new Team("A", 10, 1));
        faultyList.add(new Team("B", 9, 5));
        faultyList.sort(faulty);
        System.out.println("Faulty comparator example: " + faultyList);
        System.out.println("Reason: comparing points by subtraction is not the same as comparing points first and goal difference second, and can violate the transitivity requirement.");
    }
}
