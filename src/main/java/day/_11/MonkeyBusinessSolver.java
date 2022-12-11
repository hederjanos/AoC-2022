package day._11;

import util.common.Solver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyBusinessSolver extends Solver<Integer> {

    private List<Monkey> monkeys;

    public MonkeyBusinessSolver(String filename) {
        super(filename);
        monkeys = IntStream.iterate(0, i -> i <= puzzle.size() + 1 - 7, i -> i + 7)
                .mapToObj(i -> new Monkey(puzzle.subList(i, i + 7 - 1)))
                .collect(Collectors.toList());
        System.out.println();
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
