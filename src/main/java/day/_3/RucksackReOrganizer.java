package day._3;

import util.common.Solver;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class RucksackReOrganizer extends Solver<Integer> {

    public RucksackReOrganizer(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::convertToPriority)
                .reduce(0, Integer::sum);
    }

    private int convertToPriority(String items) {
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
        char intersection = firstCompartment.stream().findFirst().orElseThrow();
        return Character.isUpperCase(intersection) ? intersection - 'A' + 27 : intersection - 'a' + 1;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
