package day._14;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cave {

    private final Set<Coordinate> rocks;
    private final int[] borders;
    private final Set<Coordinate> sands;

    public Cave(List<String> puzzle) {
        rocks = initRocks((puzzle));
        borders = determineBorders();
        sands = new HashSet<>();
    }

    private Set<Coordinate> initRocks(List<String> puzzle) {
        return puzzle.stream()
                .map(this::processALine)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Coordinate> processALine(String line) {
        String[] coordinates = line.split(" -> ");
        List<Coordinate> boundaries = Arrays.stream(coordinates).map(Coordinate::new).collect(Collectors.toList());
        return IntStream.range(0, boundaries.size() - 1)
                .mapToObj(i -> {
                    Coordinate start = boundaries.get(i);
                    Coordinate end = boundaries.get(i + 1);
                    return (Set<Coordinate>) new HashSet<>(start.getCoordinatesInLineBetweenWith(end));
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private int[] determineBorders() {
        int[] minmax = new int[4];
        minmax[0] = Integer.MAX_VALUE;
        minmax[1] = Integer.MIN_VALUE;
        minmax[2] = 0;
        minmax[3] = Integer.MIN_VALUE;
        rocks.forEach(coordinate -> {
            if (coordinate.getX() < minmax[0]) {
                minmax[0] = coordinate.getX();
            }
            if (coordinate.getX() > minmax[1]) {
                minmax[1] = coordinate.getX();
            }
            if (coordinate.getY() > minmax[3]) {
                minmax[3] = coordinate.getY();
            }
        });
        return minmax;
    }

    public int simulateFalling(boolean withFloor) {
        Coordinate fallenSand;
        do {
            fallenSand = fallOneSand(new Coordinate(500, 0), withFloor);
            if (fallenSand != null) {
                sands.add(fallenSand);
                if (fallenSand.equals(new Coordinate(500, 0))) {
                    break;
                }
            }
        } while (fallenSand != null);
        return sands.size();
    }

    private Coordinate fallOneSand(Coordinate sand, boolean withFloor) {
        boolean flowsOut = false;
        Direction[] possibleDirs = new Direction[]{Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        boolean isFalling = true;
        while (isFalling) {
            boolean trial = false;
            for (Direction dir : possibleDirs) {
                Coordinate newSand = sand.moveByDirection(dir);
                if (!rocks.contains(newSand) && !sands.contains(newSand)) {
                    if (!isInsideCave(newSand)) {
                        if (!withFloor) {
                            flowsOut = true;
                        } else if (!isAtFloor(newSand)) {
                            trial = true;
                            sand = newSand;
                        }
                        break;
                    }
                    trial = true;
                    sand = newSand;
                    break;
                }
            }
            if (!trial) isFalling = false;
        }
        return !flowsOut ? sand : null;
    }

    private boolean isInsideCave(Coordinate coordinate) {
        return coordinate.getX() <= borders[1] && coordinate.getX() >= borders[0]
               && coordinate.getY() <= borders[3] && coordinate.getY() >= borders[2];
    }

    private boolean isAtFloor(Coordinate coordinate) {
        return coordinate.getY() == borders[3] + 2;
    }

    @Override
    public String toString() {
        return IntStream.rangeClosed(borders[2], borders[3])
                .mapToObj(i -> IntStream.rangeClosed(borders[0], borders[1])
                        .mapToObj(j -> getPrintAt(i, j))
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getPrintAt(int i, int j) {
        String print;
        if (rocks.contains(new Coordinate(j, i))) {
            print = "#";
        } else {
            print = sands.contains(new Coordinate(j, i)) ? "o" : ".";
        }
        return print;
    }

}
