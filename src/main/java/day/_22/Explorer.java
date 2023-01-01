package day._22;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.Set;

public class Explorer {

    private Coordinate coordinate;
    private Direction direction;

    public Explorer(Coordinate coordinate, Direction direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }

    public void turnRight() {
        Direction[] directions = Direction.values();
        direction = directions[(direction.ordinal() + 2) % directions.length];
    }

    public void turnLeft() {
        Direction[] directions = Direction.values();
        direction = directions[((direction.ordinal() - 2) % directions.length + directions.length) % directions.length];
    }

    public boolean move(Set<Coordinate> walls, Set<Coordinate> tiles) {
        boolean isMoved = false;
        Coordinate next = coordinate.moveByDirection(direction);
        if (!walls.contains(next) && !tiles.contains(next)) {
            do {
                next = next.moveByDirection(direction.getOppositeDirection());
            } while (walls.contains(next) || tiles.contains(next));
            next = next.moveByDirection(direction);
            if (!walls.contains(next)) {
                coordinate = next;
                isMoved = true;
            }
        } else if (tiles.contains(next)) {
            coordinate = next;
            isMoved = true;
        }
        return isMoved;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }

}
