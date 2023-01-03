package day._12;

import util.coordinate.Coordinate;

public final class PathCell {

    private final Coordinate coordinate;
    private final Integer numberOfSteps;

    public PathCell(Coordinate coordinate, Integer numberOfSteps) {
        this.coordinate = coordinate;
        this.numberOfSteps = numberOfSteps;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

}
