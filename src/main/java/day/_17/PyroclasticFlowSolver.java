package day._17;

import util.common.Solver;
import util.coordinate.Direction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PyroclasticFlowSolver extends Solver<Long> {

    private Chamber chamber;
    private final List<Direction> jetPattern;

    public PyroclasticFlowSolver(String filename) {
        super(filename);
        jetPattern = parseJetPattern();
    }

    private List<Direction> parseJetPattern() {
        return IntStream.range(0, puzzle.get(0).length())
                .mapToObj(i -> puzzle.get(0).charAt(i) == '<' ? Direction.LEFT : Direction.RIGHT)
                .collect(Collectors.toList());
    }

    @Override
    protected Long solvePartOne() {
        chamber = new Chamber(jetPattern);
        return chamber.moveByJetPattern(2023L);
    }

    @Override
    protected Long solvePartTwo() {
        chamber = new Chamber(jetPattern);
        return null;
    }

}
