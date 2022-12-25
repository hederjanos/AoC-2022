package day._23;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Grove {

    private static final List<List<Direction>> RULES = List.of(
            List.of(Direction.UP, Direction.UPPER_RIGHT, Direction.UPPER_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_LEFT),
            List.of(Direction.LEFT, Direction.UPPER_LEFT, Direction.DOWN_LEFT),
            List.of(Direction.RIGHT, Direction.UPPER_RIGHT, Direction.DOWN_RIGHT));
    private final int[] borders;
    private final Set<Coordinate> elfPositions;

    protected Grove(List<String> puzzle) {
        borders = initBorders();
        elfPositions = IntStream.range(0, puzzle.size())
                .mapToObj(i -> {
                    String line = puzzle.get(i);
                    return IntStream.range(0, line.length())
                            .filter(j -> line.charAt(j) == '#')
                            .mapToObj(j -> {
                                Coordinate coordinate = new Coordinate(j, i);
                                refreshBorderPositions(coordinate);
                                return coordinate;
                            }).collect(Collectors.toSet());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private int[] initBorders() {
        int[] minmax = new int[4];
        minmax[0] = Integer.MAX_VALUE;
        minmax[1] = Integer.MIN_VALUE;
        minmax[2] = Integer.MAX_VALUE;
        minmax[3] = Integer.MIN_VALUE;
        return minmax;
    }

    private void refreshBorderPositions(Coordinate coordinate) {
        if (coordinate.getX() < borders[0]) {
            borders[0] = coordinate.getX();
        }
        if (coordinate.getX() > borders[1]) {
            borders[1] = coordinate.getX();
        }
        if (coordinate.getY() < borders[2]) {
            borders[2] = coordinate.getY();
        }
        if (coordinate.getY() > borders[3]) {
            borders[3] = coordinate.getY();
        }
    }

    public void simulate(int rounds) {
        int i = 0;
        while (i < rounds) {
            getProposedNexPositions(i).forEach((key, value) -> {
                elfPositions.remove(key);
                elfPositions.add(value);
            });
            i++;
        }
        elfPositions.forEach(this::refreshBorderPositions);
    }

    private Map<Coordinate, Coordinate> getProposedNexPositions(int rounds) {
        Map<Coordinate, Coordinate> proposedNextPositions = new HashMap<>();
        Map<Coordinate, Integer> nextPositionRegister = new HashMap<>();
        elfPositions.stream()
                .filter(this::hasAnyNeighbours)
                .forEach(elfPosition -> {
                    Coordinate proposedNextPosition = getProposedNextPosition(rounds, elfPosition);
                    proposedNextPositions.putIfAbsent(elfPosition, proposedNextPosition);
                    nextPositionRegister.compute(proposedNextPosition, (k, v) -> v == null ? 1 : v + 1);
                });
        proposedNextPositions.entrySet().removeIf(entry -> nextPositionRegister.get(entry.getValue()) > 1);
        return proposedNextPositions;
    }

    private boolean hasAnyNeighbours(Coordinate coordinate) {
        return coordinate.getAdjacentCoordinates().stream()
                .anyMatch(elfPositions::contains);
    }

    private Coordinate getProposedNextPosition(int rounds, Coordinate coordinate) {
        Coordinate newCoordinate;
        int occupiedCoordinates = 0;
        int i = 0;
        while (i < RULES.size()) {
            List<Direction> directions = RULES.get((rounds + i) % RULES.size());
            occupiedCoordinates = 0;
            for (Direction direction : directions) {
                newCoordinate = coordinate.moveByDirection(direction);
                if (elfPositions.contains(newCoordinate)) {
                    occupiedCoordinates++;
                }
            }
            if (occupiedCoordinates == 0) {
                break;
            }
            i++;
        }
        return occupiedCoordinates == 0 ? coordinate.moveByDirection(RULES.get((rounds + i) % RULES.size()).get(0)) : null;
    }

    public int getNumberOfEmptyCoordinates() {
        return getArea() - elfPositions.size();
    }

    private int getArea() {
        int x = borders[1] - borders[0] + 1;
        int y = borders[3] - borders[2] + 1;
        return x * y;
    }

    @Override
    public String toString() {
        return IntStream.rangeClosed(borders[2], borders[3])
                .mapToObj(i -> IntStream.rangeClosed(borders[0], borders[1])
                        .mapToObj(j -> elfPositions.contains(new Coordinate(j, i)) ? "#" : ".")
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}