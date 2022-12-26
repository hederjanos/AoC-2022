package day._24;

import util.common.Solver;

public class BlizzardBasinSolver extends Solver<Integer> {

    private final BlizzardBasin blizzardBasin;

    public BlizzardBasinSolver(String filename) {
        super(filename);
        blizzardBasin = new BlizzardBasin(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        return blizzardBasin.findShortestExpedition().orElseThrow().getElapsedTime();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
