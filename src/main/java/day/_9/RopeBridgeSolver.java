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
        rope = new Rope();
        return puzzle.stream()
                .map(Motion::new)
                .flatMap(motion -> rope.move(motion).stream())
                .collect(Collectors.toSet()).size() + 1;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
