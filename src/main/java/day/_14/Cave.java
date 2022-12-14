package day._14;

import util.coordinate.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cave {

    private final Set<Coordinate> rocks;
    private final int[] borders;

    public Cave(List<String> puzzle) {
        rocks = initRocks((puzzle));
        borders = determineBorders();
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
                    Set<Coordinate> rockUnits = new HashSet<>();
                    Coordinate start = boundaries.get(i);
                    Coordinate end = boundaries.get(i + 1);
                    rockUnits.add(start);
                    rockUnits.add(end);
                    rockUnits.addAll(createCoordinatesInVerticalDirection(start, end));
                    rockUnits.addAll(createCoordinatesInHorizontalDirection(start, end));
                    return rockUnits;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Coordinate> createCoordinatesInVerticalDirection(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        int head = Math.min(start.getY(), end.getY());
        Coordinate newEnd = (end.getY() == head) ? start : end;
        while (head < newEnd.getY()) {
            coordinatesInLine.add(new Coordinate(newEnd.getX(), head + 1));
            head++;
        }
        return coordinatesInLine;
    }

    private Set<Coordinate> createCoordinatesInHorizontalDirection(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        int head = Math.min(start.getX(), end.getX());
        Coordinate newEnd = (end.getX() == head) ? start : end;
        while (head < newEnd.getX()) {
            coordinatesInLine.add(new Coordinate(head + 1, newEnd.getY()));
            head++;
        }
        return coordinatesInLine;
    }

    private int[] determineBorders() {
        int[] minmax = new int[4];
        minmax[0] = Integer.MAX_VALUE;
        minmax[1] = Integer.MIN_VALUE;
        minmax[2] = Integer.MAX_VALUE;
        minmax[3] = Integer.MIN_VALUE;
        rocks.forEach(coordinate -> {
            if (coordinate.getX() < minmax[0]) {
                minmax[0] = coordinate.getX();
            }
            if (coordinate.getX() > minmax[1]) {
                minmax[1] = coordinate.getX();
            }
            if (coordinate.getY() < minmax[2]) {
                minmax[2] = coordinate.getY();
            }
            if (coordinate.getY() > minmax[3]) {
                minmax[3] = coordinate.getY();
            }
        });
        System.out.println(Arrays.toString(minmax));
        return minmax;
    }

    @Override
    public String toString() {
        return IntStream.rangeClosed(borders[2], borders[3])
                .mapToObj(i -> IntStream.rangeClosed(borders[0], borders[1])
                        .mapToObj(j -> rocks.contains(new Coordinate(j, i)) ? "#" : ".")
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
