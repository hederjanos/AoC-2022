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

    public boolean move(Set<Coordinate> walls, Set<Coordinate> tiles, boolean inThreeDimension) {
        boolean isMoved = false;
        Coordinate next = coordinate.moveByDirection(direction);
        Direction newDirection = direction;
        if (!walls.contains(next) && !tiles.contains(next)) {
            if (inThreeDimension) {
                int x = next.getX();
                int y = next.getY();
                if (x > 50 && x < 101 && y == 0) {
                    newDirection = Direction.RIGHT;
                    next = new Coordinate(1, x + 100);
                } else if (x == 50 && y > 0 && y < 51) {
                    newDirection = Direction.RIGHT;
                    next = new Coordinate(1, 151 - y);
                } else if (x > 100 && x < 151 && y == 0) {
                    newDirection = Direction.UP;
                    next = new Coordinate(x - 100, 200);
                } else if (x == 151 && y > 0 && y < 51) {
                    newDirection = Direction.LEFT;
                    next = new Coordinate(100, 151 - y);
                } else if (x > 100 && x < 151 && y == 51 && Direction.DOWN.equals(direction)) {
                    newDirection = Direction.LEFT;
                    next = new Coordinate(100, x - 50);
                } else if (x == 50 && y > 50 && y < 101 && Direction.LEFT.equals(direction)) {
                    newDirection = Direction.DOWN;
                    next = new Coordinate(y - 50, 101);
                } else if (x == 101 && y > 50 && y < 101 && Direction.RIGHT.equals(direction)) {
                    newDirection = Direction.UP;
                    next = new Coordinate(y + 50, 50);
                } else if (x == 0 && y > 100 && y < 151) {
                    newDirection = Direction.RIGHT;
                    next = new Coordinate(51, 151 - y);
                } else if (x > 0 && x < 51 && y == 100 && Direction.UP.equals(direction)) {
                    newDirection = Direction.RIGHT;
                    next = new Coordinate(51, x + 50);
                } else if (x > 50 && x < 101 && y == 151 && Direction.DOWN.equals(direction)) {
                    newDirection = Direction.LEFT;
                    next = new Coordinate(50, x + 100);
                } else if (x == 101 && y > 100 && y < 151) {
                    newDirection = Direction.LEFT;
                    next = new Coordinate(150, 151 - y);
                } else if (x == 0 && y > 150 && y < 201) {
                    newDirection = Direction.DOWN;
                    next = new Coordinate(y - 100,1);
                } else if (x > 0 && x < 51 && y == 201) {
                    newDirection = Direction.DOWN;
                    next = new Coordinate(x + 100, 1);
                }  else if (x == 51 && y > 150 && y < 201 && Direction.RIGHT.equals(direction)) {
                    newDirection = Direction.UP;
                    next = new Coordinate(y - 100,150);
                }
            } else {
                do {
                    next = next.moveByDirection(direction.getOppositeDirection());
                } while (walls.contains(next) || tiles.contains(next));
                next = next.moveByDirection(direction);
            }
            if (!walls.contains(next)) {
                coordinate = next;
                direction = newDirection;
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
