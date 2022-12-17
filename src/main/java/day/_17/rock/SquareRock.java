package day._17.rock;

import util.coordinate.Coordinate;

public class SquareRock extends Rock {

    public SquareRock(Rock rock) {
        super(rock);
    }

    public SquareRock(Coordinate coordinate) {
       shape = new int[][] {
                {1, 1},
                {1, 1},
        };
       coordinates = initCoordinates(coordinate);
    }

}
