package day._12;

import util.coordinate.Coordinate;

public final class PathCell {

    private Coordinate coordinate;
    private Integer numberOfSteps;

    PathCell() {
    }

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
