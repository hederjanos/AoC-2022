package day._24;

import util.coordinate.Coordinate;

import java.util.Objects;

public final class Expedition {

    private final Coordinate coordinate;
    private final Integer elapsedTime;

    public Expedition(Coordinate coordinate, Integer numberOfMinutes) {
        this.coordinate = coordinate;
        this.elapsedTime = numberOfMinutes;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expedition)) return false;
        Expedition that = (Expedition) o;
        return Objects.equals(getCoordinate(), that.getCoordinate()) && Objects.equals(getElapsedTime(), that.getElapsedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate(), getElapsedTime());
    }
}
