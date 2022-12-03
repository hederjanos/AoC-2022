package day._3;

import util.common.Solver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RucksackReOrganizer extends Solver<Integer> {

    public RucksackReOrganizer(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::convertRucksackItemsToPriority)
                .reduce(0, Integer::sum);
    }

    private int convertRucksackItemsToPriority(String items) {
        Set<Character> firstCompartment = new HashSet<>();
        Set<Character> secondCompartment = new HashSet<>();
        int halfOfItems = items.length() / 2;
        IntStream.range(0, halfOfItems)
                .boxed()
                .forEach(i -> {
                    firstCompartment.add(items.charAt(i));
                    secondCompartment.add(items.charAt(i + halfOfItems));
                });
        firstCompartment.retainAll(secondCompartment);
        return calculatePriority(firstCompartment.stream().findFirst().orElseThrow());
    }

    private int calculatePriority(char intersection) {
        return Character.isUpperCase(intersection) ? intersection - 'A' + 27 : intersection - 'a' + 1;
    }

    @Override
    protected Integer solvePartTwo() {
        return IntStream.iterate(0, i -> i < puzzle.size(), i -> i + 3)
                .mapToObj(i -> puzzle.subList(i, i + 3))
                .map(this::convertGroupItemsToPriority)
                .reduce(0, Integer::sum);
    }

    public int convertGroupItemsToPriority(List<String> items) {
        List<Set<Character>> groups = items.stream()
                .map(item -> IntStream.range(0, item.length())
                        .boxed()
                        .map(item::charAt)
                        .collect(Collectors.toSet()))
                .collect(Collectors.toList());
        HashSet<Character> commonItems = groups.stream()
                .collect(() -> new HashSet<>(groups.get(0)), Set::retainAll, Set::retainAll);
        return calculatePriority(commonItems.stream().findFirst().orElseThrow());
    }

}
