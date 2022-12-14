package day._14;

import util.common.Solver;

public class RegolithReservoirSolver extends Solver<Integer> {

    private final Cave cave;

    public RegolithReservoirSolver(String filename) {
        super(filename);
        cave = new Cave(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        return cave.simulate();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
