package day._14;

import util.common.Solver;

public class RegolithReservoirSolver extends Solver<Integer> {

    private Cave cave;

    public RegolithReservoirSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        cave = new Cave(puzzle);
        return cave.simulateFalling(false);
    }

    @Override
    protected Integer solvePartTwo() {
        cave = new Cave(puzzle);
        return cave.simulateFalling(true);
    }

}
