package day._9;

import util.common.Solver;

import java.util.stream.Collectors;

public class RopeBridgeSolver extends Solver<Integer> {

    private Rope rope;

    public RopeBridgeSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        rope = new Rope(2);
        return solve();
    }

    @Override
    protected Integer solvePartTwo() {
        rope = new Rope(10);
        return solve();
    }

    private Integer solve() {
        return puzzle.stream()
                .map(Motion::new)
                .flatMap(motion -> rope.move(motion).stream())
                .collect(Collectors.toSet())
                .size();
    }

}
