package day._3;

import util.common.Solver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RucksackReOrganizationSolver extends Solver<Integer> {

    public RucksackReOrganizationSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::convertRucksackItemsToPriority)
                .reduce(0, Integer::sum);
    }

    private int convertRucksackItemsToPriority(String items) {
        Set<Character> firstCompartment = getCommonItems(items);
        return calculatePriority(firstCompartment.stream().findFirst().orElseThrow());
    }

    private Set<Character> getCommonItems(String items) {
        Set<Character> firstCompartment = new HashSet<>();
        Set<Character> secondCompartment = new HashSet<>();
        int halfOfItems = items.length() / 2;
        for (int i = 0; i < halfOfItems; i++) {
            firstCompartment.add(items.charAt(i));
            secondCompartment.add(items.charAt(i + halfOfItems));
        }
        firstCompartment.retainAll(secondCompartment);
        return firstCompartment;
    }

    private int calculatePriority(char intersection) {
        return Character.isUpperCase(intersection) ? intersection - 'A' + 27 : intersection - 'a' + 1;
    }

    @Override
    protected Integer solvePartTwo() {
        return IntStream.iterate(0, i -> i < puzzle.size(), i -> i + 3)
                .mapToObj(i -> puzzle.subList(i, i + 3))
                .mapToInt(this::convertGroupItemsToPriority)
                .sum();
    }

    public int convertGroupItemsToPriority(List<String> items) {
        Set<Character> commonItems = getCommonItems(items);
        return calculatePriority(commonItems.stream().findFirst().orElseThrow());
    }

    private Set<Character> getCommonItems(List<String> items) {
        return items.stream()
                .map(item -> IntStream.range(0, item.length())
                        .boxed()
                        .map(item::charAt)
                        .collect(Collectors.toSet()))
                .reduce((a, b) -> {
                    a.retainAll(b);
                    return a;
                })
                .orElseThrow();
    }

}
