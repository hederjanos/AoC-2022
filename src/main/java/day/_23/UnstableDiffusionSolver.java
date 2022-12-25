package day._23;

import util.common.Solver;

public class UnstableDiffusionSolver extends Solver<Integer> {

    private final Grove grove;

    public UnstableDiffusionSolver(String filename) {
        super(filename);
        grove = new Grove(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        grove.simulate(10);
        return grove.getNumberOfEmptyCoordinates();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
