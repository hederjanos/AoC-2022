package day._17.rock;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Rock {

    protected int[][] shape;
    protected Set<Coordinate> coordinates;

    protected Rock() {
    }

    protected Rock(Rock rock) {
        shape = rock.getShape();
        coordinates = rock.getCoordinates();
    }

    protected Set<Coordinate> initCoordinates(Coordinate coordinate) {
        Coordinate adjustedByHeight = coordinate.subtract(new Coordinate(0, getHeight()));
        Set<Coordinate> coords = new HashSet<>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    coords.add(adjustedByHeight.add(new Coordinate(j, i)));
                }
            }
        }
        return coords;
    }

    public void moveByDirection(Direction direction) {
        coordinates = coordinates.stream()
                .map(coordinate -> coordinate.moveByDirection(direction))
                .collect(Collectors.toSet());
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates.stream().map(Coordinate::copy).collect(Collectors.toSet());
    }

    public int[][] getShape() {
        return shape;
    }

    public int getHeight() {
        return shape.length;
    }

}
