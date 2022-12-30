package day._16;

import util.common.Solver;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ProboscideaVolcaniumSolver extends Solver<Integer> {

    private final ValveSystem valveSystem;

    public ProboscideaVolcaniumSolver(String filename) {
        super(filename);
        valveSystem = new ValveSystem(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        return valveSystem.findValveCombinationsWithMaxPressure(30).values().stream()
                .max(Integer::compare)
                .orElseThrow();
    }

    @Override
    protected Integer solvePartTwo() {
        Map<ValveCombination, Integer> combinations = valveSystem.findValveCombinationsWithMaxPressure(26);
        return combinations.entrySet().stream()
                .map(entry1 -> combinations.entrySet().stream()
                        .filter(entry2 -> entry1.getKey().differsFrom(entry2.getKey()))
                        .map(entry2 -> entry1.getValue() + entry2.getValue())
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .max(Integer::compareTo)
                .orElseThrow();
    }

}
