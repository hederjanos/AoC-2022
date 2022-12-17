package day._17.rock;

import util.coordinate.Coordinate;

public class VerticalRock extends Rock {

    public VerticalRock(Rock rock) {
        super(rock);
    }

    public VerticalRock(Coordinate coordinate) {
        shape = new int[][]{
                {1},
                {1},
                {1},
                {1},
        };
        coordinates = initCoordinates(coordinate);
    }

}
