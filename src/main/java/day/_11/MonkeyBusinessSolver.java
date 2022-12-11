package day._11;

import day._11.operation.MathOperation;
import day._11.operation.Operator;
import util.common.Solver;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyBusinessSolver extends Solver<Long> {

    private List<Monkey> monkeys;

    public MonkeyBusinessSolver(String filename) {
        super(filename);
    }

    @Override
    protected Long solvePartOne() {
        monkeys = initMonkeys();
        Monkey.setDefaultOperation(new MathOperation(Operator.DIVIDE, 3));
        Monkey.setModOperation(new MathOperation(Operator.MOD, calculateReducer(monkeys)));
        doRounds(20);
        return calculateMonkeyBusiness();
    }

    private List<Monkey> initMonkeys() {
        return IntStream.iterate(0, i -> i <= puzzle.size() + 1 - 7, i -> i + 7)
                .mapToObj(i -> new Monkey(puzzle.subList(i, i + 7 - 1)))
                .collect(Collectors.toList());
    }

    private Integer calculateReducer(List<Monkey> monkeys) {
        return monkeys.stream()
                .map(monkey -> monkey.getTestOperation().getOperand())
                .reduce(1, (a, b) -> a * b);
    }

    private void doRounds(int numberOfRounds) {
        IntStream.range(0, numberOfRounds)
                .forEach(i -> monkeys
                        .forEach(monkey -> monkey.getItems()
                                .forEach(item -> {
                                    Item newItem = monkey.doMathOnItem(item);
                                    monkey.removeItem(item);
                                    int toMonkey = monkey.doTestOnItem(newItem);
                                    monkeys.get(toMonkey).addItem(newItem);
                                })));
    }

    private Long calculateMonkeyBusiness() {
        return monkeys.stream()
                .map(Monkey::getNumberOfInspection)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1L, (a, b) -> a * b);
    }

    @Override
    protected Long solvePartTwo() {
        monkeys = initMonkeys();
        Monkey.setDefaultOperation(new MathOperation(Operator.DIVIDE, 1));
        Monkey.setModOperation(new MathOperation(Operator.MOD, calculateReducer(monkeys)));
        doRounds(10000);
        return calculateMonkeyBusiness();
    }

}
