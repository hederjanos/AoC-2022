package day._4;

import util.common.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanupSolver extends Solver<Integer> {

    List<SectionPair> sectionPairs;

    public CampCleanupSolver(String filename) {
        super(filename);
        sectionPairs = puzzle.stream()
                .map(this::parseSectionsPerLine)
                .map(SectionPair::new)
                .collect(Collectors.toList());
    }

    private List<Section> parseSectionsPerLine(String line) {
        return Arrays.stream(line.split(","))
                .map(Section::new)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return (int) sectionPairs.stream()
                .map(SectionPair::isContaining)
                .filter(a -> a)
                .count();
    }

    @Override
    protected Integer solvePartTwo() {
        return (int) sectionPairs.stream()
                .map(SectionPair::isOverlapping)
                .filter(a -> a)
                .count();
    }

}
