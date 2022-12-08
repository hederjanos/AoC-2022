package day._8;

import util.common.Solver;
import util.grid.IntegerGrid;

public class TreetopTreeHouseSolver extends Solver<Integer> {

    private final TreeHeightMap heightMap;

    public TreetopTreeHouseSolver(String filename) {
        super(filename);
        heightMap = parseInput();
    }

    private TreeHeightMap parseInput() {
        return new TreeHeightMap(puzzle, IntegerGrid.convertContiguousIntegersToList());
    }

    @Override
    protected Integer solvePartOne() {
        return heightMap.getNumberOfVisibleTrees();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
