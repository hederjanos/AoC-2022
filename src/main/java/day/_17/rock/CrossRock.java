package day._17.rock;

import util.coordinate.Coordinate;

public class CrossRock extends Rock {

    public CrossRock(Rock rock) {
        super(rock);
    }

    public CrossRock(Coordinate coordinate) {
       shape = new int[][]{
               {0, 1, 0},
               {1, 1, 1},
               {0, 1, 0},
       };
       coordinates = initCoordinates(coordinate);
   }

}
