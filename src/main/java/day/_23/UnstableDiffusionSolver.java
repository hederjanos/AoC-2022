package day._23;

import util.common.Solver;

public class UnstableDiffusionSolver extends Solver<Integer> {

    private Grove grove;

    public UnstableDiffusionSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        grove = new Grove(puzzle);
        grove.simulate(10);
        return grove.getNumberOfEmptyCoordinates();
    }

    @Override
    protected Integer solvePartTwo() {
        grove = new Grove(puzzle);
        return grove.simulate(Integer.MAX_VALUE);
    }

}
