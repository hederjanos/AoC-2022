package day._6;

import util.common.Solver;

import java.util.stream.IntStream;

public class TuningTroubleSolver extends Solver<Integer> {

    private final String dataStream;

    public TuningTroubleSolver(String filename) {
        super(filename);
        dataStream = puzzle.get(0);
    }

    @Override
    protected Integer solvePartOne() {
        return solve(4);
    }

    private int solve(int distinct) {
        return IntStream.range(distinct - 1, dataStream.length())
                       .filter(i -> dataStream.substring(i - distinct + 1, i + 1).chars().boxed().distinct().count() == distinct)
                       .findFirst()
                       .orElseThrow() + 1;
    }

    @Override
    protected Integer solvePartTwo() {
        return solve(14);
    }

}
