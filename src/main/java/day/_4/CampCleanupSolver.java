package day._4;

import util.common.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanupSolver extends Solver<Integer> {

    public CampCleanupSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        return (int) puzzle.stream()
                .map(this::parseSectionsPerLine)
                .map(sections -> sections.get(0).fullyContains(sections.get(1)))
                .filter(a -> a)
                .count();
    }

    private List<Section> parseSectionsPerLine(String line) {
        return Arrays.stream(line.split(","))
                .map(Section::new)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
