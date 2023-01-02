package day._12;

import util.common.Solver;
import util.grid.IntegerGrid;

public class HillClimbingAlgorithmSolver extends Solver<Integer> {

    private final HeightMap heightMap;

    public HillClimbingAlgorithmSolver(String filename) {
        super(filename);
        heightMap = parseInput();
    }

    private HeightMap parseInput() {
        return new HeightMap(puzzle, IntegerGrid.convertContiguousCharactersToList());
    }

    @Override
    protected Integer solvePartOne() {
        return heightMap.findFewestStepPathFromDefault().orElseThrow().getNumberOfSteps();
    }

    @Override
    protected Integer solvePartTwo() {
        return heightMap.collectStartCells().stream()
                .mapToInt(startCell -> heightMap.findFewestStepPathFromStart(startCell).orElseThrow().getNumberOfSteps())
                .filter(steps -> steps > 0)
                .min()
                .orElseThrow();
    }

}
