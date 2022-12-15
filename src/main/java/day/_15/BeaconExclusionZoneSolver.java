package day._15;

import util.common.Solver;

import java.util.Objects;
import java.util.stream.IntStream;

public class BeaconExclusionZoneSolver extends Solver<Long> {

    public static final int MIN_DIMENSION = 0;
    public static final int MAX_DIMENSION = 4_000_000;
    private final TunnelNetwork tunnelNetwork;

    public BeaconExclusionZoneSolver(String filename) {
        super(filename);
        tunnelNetwork = new TunnelNetwork(puzzle);
    }

    @Override
    protected Long solvePartOne() {
        return tunnelNetwork.getNumberOfPositionsWithoutBeaconAtHorizontal(2_000_000);
    }

    @Override
    protected Long solvePartTwo() {
        return IntStream.rangeClosed(MIN_DIMENSION, MAX_DIMENSION)
                .mapToObj(i -> tunnelNetwork.calculateBeaconCoordinateAtHorizontal(i, MIN_DIMENSION, MAX_DIMENSION))
                .filter(Objects::nonNull)
                .findFirst()
                .map(beacon -> (long) beacon.getX() * MAX_DIMENSION + beacon.getY())
                .orElse(null);
    }

}
