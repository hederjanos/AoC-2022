package day._14;

import util.common.Solver;
import util.coordinate.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RegolithReservoirSolver extends Solver<Integer> {

    private Cave cave;

    public RegolithReservoirSolver(String filename) {
        super(filename);
        cave = new Cave(puzzle);
        System.out.println(cave);
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
