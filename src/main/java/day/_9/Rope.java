package day._9;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Rope {

    private final Coordinate[] knots;

    public Rope(int numberOfKnots) {
        knots = new Coordinate[numberOfKnots];
        IntStream.iterate(0, i -> i < numberOfKnots, i -> i + 1)
                .boxed()
                .forEach(i -> knots[i] = new Coordinate(0, 0));
    }

    public Set<Coordinate> move(Motion motion) {
        Set<Coordinate> visitedPositionsByTail = new HashSet<>();
        IntStream.range(0, motion.getNumberOfSteps())
                .forEach(i -> {
                    knots[0] = knots[0].moveByDirection(motion.getDirection());
                    IntStream.range(1, knots.length)
                            .boxed()
                            .forEach(j -> {
                                knots[j] = moveOneStep(j);
                                visitedPositionsByTail.add(knots[knots.length - 1]);
                            });
                });
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
