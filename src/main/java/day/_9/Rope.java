package day._9;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Rope {

    private final Coordinate[] knots;

    public Rope(int numberOfKnots) {
        knots = new Coordinate[numberOfKnots];
        IntStream.range(0, numberOfKnots).forEach(i -> knots[i] = new Coordinate(0, 0));
    }

    public Set<Coordinate> move(Motion motion) {
        Set<Coordinate> visitedPositionsByTail = new HashSet<>();
        int bound = motion.getNumberOfSteps();
        for (int i = 0; i < bound; i++) {
            knots[0] = knots[0].moveByDirection(motion.getDirection());
            int bound1 = knots.length;
            for (int j = 1; j < bound1; j++) {
                knots[j] = moveOneStep(j);
                visitedPositionsByTail.add(knots[knots.length - 1]);
            }
        }
        return visitedPositionsByTail;
    }

    private Coordinate moveOneStep(int lastIndex) {
        Coordinate first = knots[lastIndex - 1];
        Coordinate second = knots[lastIndex];
        Coordinate relativeCoordinate = first.isAdjacent(second);
        if (relativeCoordinate != null) {
            second = second.moveByCoordinate(relativeCoordinate);
        }
        return second;
    }

}
