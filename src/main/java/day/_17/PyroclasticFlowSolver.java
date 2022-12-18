package day._17;

import util.common.Solver;
import util.coordinate.Direction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PyroclasticFlowSolver extends Solver<Long> {

    public static final long VERY_BIG_NUMBER = 1_000_000_000_000L;
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
        int firstEnd = IntStream.iterate(chamber.getFlow().size() - 1, i -> i >= 0, i -> i - 1)
                               .dropWhile(i -> chamber.getFlow().get(i).getReference() != null)
                               .boxed().findFirst().orElseThrow() + 1;
        ComplexChamberState complexChamberState = chamber.getFlow().get(firstEnd);

        int numberOfRocksBeforePattern = complexChamberState.getReference().getNumberOfRocks();
        int heightBeforePattern = complexChamberState.getReference().getHeight();

        int numberOfRocksAfterPattern = complexChamberState.getHeightAndRocks().getNumberOfRocks();
        int heightAfterPattern = complexChamberState.getHeightAndRocks().getHeight();

        int rocksInCycle = numberOfRocksAfterPattern - numberOfRocksBeforePattern;
        int heightOfCycle = heightAfterPattern - heightBeforePattern;

        long remainderRocks = (VERY_BIG_NUMBER - numberOfRocksBeforePattern) % rocksInCycle;

        long heightOfFullCycles = (VERY_BIG_NUMBER - (numberOfRocksBeforePattern + remainderRocks)) / rocksInCycle * heightOfCycle;

        long heightAdditionAfterFullCycles = chamber.getFlow().stream()
                                                     .filter(state -> state.getHeightAndRocks().getNumberOfRocks() == numberOfRocksBeforePattern + remainderRocks)
                                                     .map(complex -> complex.getHeightAndRocks().getHeight())
                                                     .findFirst()
                                                     .orElseThrow() - (long) heightBeforePattern;

        return heightBeforePattern + heightOfFullCycles + heightAdditionAfterFullCycles;
    }

}
