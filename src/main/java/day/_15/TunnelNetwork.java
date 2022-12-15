package day._15;

import util.coordinate.Coordinate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TunnelNetwork {

    private final Map<Coordinate, Sensor> sensors;

    public TunnelNetwork(List<String> puzzle) {
        sensors = initSensors((puzzle));
    }

    private Map<Coordinate, Sensor> initSensors(List<String> puzzle) {
        return puzzle.stream()
                .map(this::extractCoordinatesFromLine)
                .collect(Collectors.toMap(pair -> pair.get(0), pair -> new Sensor(pair.get(0), pair.get(1))));
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

    public int calculatePositionsNotContainingBeaconAtHorizontal(int y) {
        Map<Coordinate, Sensor> filteredSensors = filterSensorsByDistanceFromHorizontal(y);
        List<HorizontalLineSegment> lineSegments = calculateLineSegments(filteredSensors, y);
        List<HorizontalLineSegment> reducedLineSegments = reduceLineSegments(lineSegments);
        return reducedLineSegments.stream()
                .map(lineSegment -> lineSegment.getEnd().getX() - lineSegment.getStart().getX())
                .reduce(0, Integer::sum);
    }

    private Map<Coordinate, Sensor> filterSensorsByDistanceFromHorizontal(int y) {
        return sensors.entrySet().stream()
                .filter(entry -> entry.getKey().getManhattanDistanceFromHorizontal(y) <= entry.getValue().getDistance())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<HorizontalLineSegment> calculateLineSegments(Map<Coordinate, Sensor> filteredSensors, int y) {
        return filteredSensors.entrySet().stream()
                .map(entry -> {
                    List<Coordinate> coordinates = entry.getKey().calculateMinAndMaxExcisedCoordinatesFromHorizontal(y, entry.getValue().getDistance());
                    return new HorizontalLineSegment(coordinates);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    private List<HorizontalLineSegment> reduceLineSegments(List<HorizontalLineSegment> lineSegments) {
        List<HorizontalLineSegment> reducedLineSegments = new ArrayList<>();
        reducedLineSegments.add(lineSegments.get(0));
        HorizontalLineSegment current = reducedLineSegments.get(0);
        for (int i = 1; i < lineSegments.size(); i++) {
            HorizontalLineSegment newLineSegment = current.createCommonLineSegmentIfOverlapping(lineSegments.get(i));
            if (newLineSegment != null) {
                current = newLineSegment;
                reducedLineSegments.set(reducedLineSegments.size() - 1, current);
            } else {
                reducedLineSegments.add(lineSegments.get(i));
                current = reducedLineSegments.get(reducedLineSegments.size() - 1);
            }
        }
        return reducedLineSegments;
    }

}
