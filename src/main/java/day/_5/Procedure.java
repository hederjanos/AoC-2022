package day._5;

import java.util.List;

public class Procedure {

    private final int numberOfMoves;
    private final int from;
    private final int to;

    public Procedure(List<Integer> command) {
        numberOfMoves = command.get(0);
        from = command.get(1) - 1;
        to = command.get(2) - 1;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

}
