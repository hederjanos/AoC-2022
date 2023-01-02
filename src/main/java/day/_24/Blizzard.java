package day._24;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.Objects;

public class Blizzard {

    private final Coordinate coordinate;
    private final Direction direction;

    public Blizzard(Coordinate coordinate, Direction direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }

    public Coordinate getCoordinate() {
        return coordinate.copy();
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blizzard)) return false;
        Blizzard blizzard = (Blizzard) o;
        return Objects.equals(getCoordinate(), blizzard.getCoordinate()) && getDirection() == blizzard.getDirection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate(), getDirection());
    }

}
