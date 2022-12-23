package day._16;

import util.common.Solver;

public class ProboscideaVolcaniumSolver extends Solver<Integer> {

    private final ValveSystem valveSystem;

    public ProboscideaVolcaniumSolver(String filename) {
        super(filename);
        valveSystem = new ValveSystem(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        ValveSystemState mostPressureState = valveSystem.findMostReleasedPressureState();
        return valveSystem.getPossibleReleasedPressure(mostPressureState);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
