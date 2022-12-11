package day._11;

import util.common.Solver;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyBusinessSolver extends Solver<Integer> {

    private final List<Monkey> monkeys;

    public MonkeyBusinessSolver(String filename) {
        super(filename);
        monkeys = IntStream.iterate(0, i -> i <= puzzle.size() + 1 - 7, i -> i + 7)
                .mapToObj(i -> new Monkey(puzzle.subList(i, i + 7 - 1)))
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        IntStream.range(0, 20)
                .forEach(i -> monkeys
                        .forEach(monkey -> monkey.getItems()
                                .forEach(item -> {
                                    Item newItem = monkey.doMathOnItem(item);
                                    monkey.removeItem(item);
                                    int toMonkey = monkey.doTestOnItem(newItem);
                                    monkeys.get(toMonkey).addItem(newItem);
                                })));
        return monkeys.stream()
                .map(Monkey::getNumberOfInspection)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
