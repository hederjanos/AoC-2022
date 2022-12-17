package day._17;

import util.common.Solver;

public class PyroclasticFlowSolver extends Solver<Integer> {

    public PyroclasticFlowSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        Chamber chamber = new Chamber();
        chamber.moveRocksTest();
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
