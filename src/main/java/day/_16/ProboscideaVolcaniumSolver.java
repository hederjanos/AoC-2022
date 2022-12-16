package day._16;

import util.common.Solver;

public class ProboscideaVolcaniumSolver extends Solver<Integer> {

    private ValveSystem valveSystem;

    public ProboscideaVolcaniumSolver(String filename) {
        super(filename);
        valveSystem = new ValveSystem(puzzle);
        System.out.println(valveSystem);
    }

    @Override
    protected Integer solvePartOne() {
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
