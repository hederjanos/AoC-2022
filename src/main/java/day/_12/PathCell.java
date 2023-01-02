package day._12;

import util.coordinate.Coordinate;

public final class PathCell {

    private final Coordinate coordinate;
    private final Integer numberOfSteps;

    public PathCell(Coordinate coordinate, Integer numberOfSteps) {
        this.coordinate = coordinate.copy();
        this.numberOfSteps = numberOfSteps;
    }

    public Coordinate getCoordinate() {
        return coordinate.copy();
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

}
