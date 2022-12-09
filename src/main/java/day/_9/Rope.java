package day._9;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Rope {

    private Coordinate head;
    private Coordinate tail;

    public Rope() {
        head = new Coordinate(0,0);
        tail = new Coordinate(0,0);
    }

    public Coordinate getHead() {
        return head;
    }

    public Coordinate getTail() {
        return tail;
    }

    public Set<Coordinate> move(Motion motion) {
        Set<Coordinate> visitedCellsByTail = new HashSet<>();
        int initStep = 0;
        while (initStep < motion.getNumberOfSteps()) {
            Coordinate newHead = new Coordinate(head);
            newHead.setX(head.getX() + motion.getDirection().getX());
            newHead.setY(head.getY() + motion.getDirection().getY());
            head = newHead;
            Coordinate relativeCoordinate = head.isAdjacent(tail);
            if (relativeCoordinate != null) {
                if (relativeCoordinate.getX() > 1) {
                    relativeCoordinate.setX(relativeCoordinate.getX() - 1);
                } else if (relativeCoordinate.getX() < -1) {
                    relativeCoordinate.setX(relativeCoordinate.getX() + 1);
                }
                if (relativeCoordinate.getY() > 1) {
                    relativeCoordinate.setY(relativeCoordinate.getY() - 1);
                } else if (relativeCoordinate.getY() < -1) {
                    relativeCoordinate.setY(relativeCoordinate.getY() + 1);
                }
                tail = new Coordinate(tail.add(relativeCoordinate));
                visitedCellsByTail.add(tail);
            }
            initStep++;
        }
        return visitedCellsByTail;
    }

}
