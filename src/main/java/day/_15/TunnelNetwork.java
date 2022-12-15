package day._15;

import util.coordinate.Coordinate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TunnelNetwork {

    private final Map<Coordinate, Coordinate> sensors;
    private final int[] borders;

    public TunnelNetwork(List<String> puzzle) {
        sensors = initSensors((puzzle));
        borders = determineBorders();
        System.out.println(Arrays.toString(borders));
    }

    private Map<Coordinate, Coordinate> initSensors(List<String> puzzle) {
        return puzzle.stream()
                .map(this::extractCoordinatesFromLine)
                .collect(Collectors.toMap(pair -> pair.get(0), pair -> pair.get(1)));
    }

    private List<Coordinate> extractCoordinatesFromLine(String line) {
        List<Coordinate> coordinates = new ArrayList<>();
        Pattern pattern = Pattern.compile("x=-?[0-9]+, y=-?[0-9]+");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            String[] components = group.split(", ");
            List<Integer> integers = Arrays.stream(components)
                    .map(component -> Integer.parseInt(component.substring(2)))
                    .collect(Collectors.toList());
            coordinates.add(new Coordinate(integers.get(0), integers.get(1)));
        }
        return coordinates;
    }

    private int[] determineBorders() {
        int[] minmax = new int[4];
        minmax[0] = Integer.MAX_VALUE;
        minmax[1] = Integer.MIN_VALUE;
        minmax[2] = Integer.MAX_VALUE;
        minmax[3] = Integer.MIN_VALUE;

        sensors.forEach((key, value) -> {
            if (key.getX() < minmax[0]) {
                minmax[0] = key.getX();
            }
            if (key.getX() > minmax[1]) {
                minmax[1] = key.getX();
            }
            if (key.getY() < minmax[2]) {
                minmax[2] = key.getY();
            }
            if (key.getY() > minmax[3]) {
                minmax[3] = key.getY();
            }
            if (value.getX() < minmax[0]) {
                minmax[0] = value.getX();
            }
            if (value.getX() > minmax[1]) {
                minmax[1] = value.getX();
            }
            if (value.getY() < minmax[2]) {
                minmax[2] = value.getY();
            }
            if (value.getY() > minmax[3]) {
                minmax[3] = value.getY();
            }
        });
        return minmax;
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
        if (sensors.containsKey(new Coordinate(j, i))) {
            print = "S";
        } else {
            print = sensors.containsValue(new Coordinate(j, i)) ? "B" : ".";
        }
        return print;
    }

}
