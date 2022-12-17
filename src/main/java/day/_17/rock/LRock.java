package day._17.rock;

import util.coordinate.Coordinate;

public class LRock extends Rock {

    public LRock(Rock rock) {
        super(rock);
    }

    public LRock(Coordinate coordinate) {
        shape = new int[][]{
                {0, 0, 1},
                {0, 0, 1},
                {1, 1, 1},
        };
        coordinates = initCoordinates(coordinate);
    }

}
