package day._4;

import util.common.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanupSolver extends Solver<Integer> {

    List<List<Section>> sectionsPerLine;

    public CampCleanupSolver(String filename) {
        super(filename);
        sectionsPerLine = puzzle.stream()
                .map(this::parseSectionsPerLine)
                .collect(Collectors.toList());
    }

    private List<Section> parseSectionsPerLine(String line) {
        return Arrays.stream(line.split(","))
                .map(Section::new)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return (int) sectionsPerLine.stream()
                .map(sections -> sections.get(0).fullyContains(sections.get(1)))
                .filter(a -> a)
                .count();
    }

    @Override
    protected Integer solvePartTwo() {
        return (int) sectionsPerLine.stream()
                .map(sections -> sections.get(0).overlapsWith(sections.get(1)))
                .filter(a -> a)
                .count();
    }

}
