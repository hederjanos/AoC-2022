package day._12;

import util.coordinate.Coordinate;

public final class PathCell {

    private final Coordinate coordinate;
    private Integer numberOfSteps;

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

    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public String toString() {
        return "PathCell{" +
               "coordinate=" + coordinate +
               ", numberOfSteps=" + numberOfSteps +
               '}';
    }
}
