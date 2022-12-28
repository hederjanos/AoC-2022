package day._24;

import util.common.Solver;

public class BlizzardBasinSolver extends Solver<Integer> {

    private BlizzardBasin blizzardBasin;

    public BlizzardBasinSolver(String filename) {
        super(filename);
        blizzardBasin = new BlizzardBasin(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        return blizzardBasin.findShortestExpedition(new Expedition(blizzardBasin.getStart(), 0), blizzardBasin.getTarget()).orElseThrow().getElapsedTime();
    }

    @Override
    protected Integer solvePartTwo() {
        blizzardBasin = new BlizzardBasin(puzzle);
        Expedition round1 = blizzardBasin.findShortestExpedition(new Expedition(blizzardBasin.getStart(), 0), blizzardBasin.getTarget()).orElseThrow();
        Expedition round2 = blizzardBasin.findShortestExpedition(round1, blizzardBasin.getStart()).orElseThrow();
        return blizzardBasin.findShortestExpedition(round2, blizzardBasin.getTarget()).orElseThrow().getElapsedTime();
    }

}
