package day._17;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Rock {

    private static final List<int[][]> ROCK_SHAPES = List.of(
            new int[][]{
                    {1, 1, 1, 1}},
            new int[][]{
                    {0, 1, 0},
                    {1, 1, 1},
                    {0, 1, 0}},
            new int[][]{
                    {0, 0, 1},
                    {0, 0, 1},
                    {1, 1, 1}},
            new int[][]{
                    {1},
                    {1},
                    {1},
                    {1}},
            new int[][]{
                    {1, 1},
                    {1, 1}});
    private int shapeIndex;
    private final Set<Coordinate> coordinates;

    public Rock(long rockCounter, Coordinate coordinate) {
        this.shapeIndex = (int) (rockCounter % ROCK_SHAPES.size());
        coordinates = initCoordinates(coordinate);
    }

    private Rock(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    private Set<Coordinate> initCoordinates(Coordinate coordinate) {
        Coordinate adjustedByHeight = coordinate.subtract(new Coordinate(0, ROCK_SHAPES.get(shapeIndex).length));
        Set<Coordinate> coords = new HashSet<>();
        for (int i = 0; i < ROCK_SHAPES.get(shapeIndex).length; i++) {
            for (int j = 0; j < ROCK_SHAPES.get(shapeIndex)[i].length; j++) {
                if (ROCK_SHAPES.get(shapeIndex)[i][j] == 1) {
                    coords.add(adjustedByHeight.add(new Coordinate(j, i)));
                }
            }
        }
        return coords;
    }

    public Rock moveByDirection(Direction direction) {
        Set<Coordinate> coords = coordinates.stream()
                .map(coordinate -> coordinate.moveByDirection(direction))
                .collect(Collectors.toSet());
        return new Rock(coords);
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates.stream().map(Coordinate::copy).collect(Collectors.toSet());
    }

}
