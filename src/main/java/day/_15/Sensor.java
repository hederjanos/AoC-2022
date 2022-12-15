package day._15;

import util.coordinate.Coordinate;

public class Sensor {

    private final Coordinate closestBeacon;
    private final int distance;

    public Sensor(Coordinate position, Coordinate closestBeacon) {
        this.closestBeacon = closestBeacon;
        distance = position.getManhattanDistanceFrom(closestBeacon);
    }

    public Coordinate getClosestBeacon() {
        return closestBeacon;
    }

    public int getDistance() {
        return distance;
    }

}
