package day._17.rock;

import util.coordinate.Coordinate;

public class HorizontalRock extends Rock {

    public HorizontalRock(Rock rock) {
        super(rock);
    }

    public HorizontalRock(Coordinate coordinate) {
        shape = new int[][]{
                {1, 1, 1, 1},
        };
        coordinates = initCoordinates(coordinate);
    }

}
