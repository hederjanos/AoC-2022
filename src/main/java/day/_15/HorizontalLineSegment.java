package day._15;

import util.coordinate.Coordinate;

import java.util.List;
import java.util.Objects;

public class HorizontalLineSegment implements Comparable<HorizontalLineSegment> {

    private final Coordinate start;
    private final Coordinate end;

    public HorizontalLineSegment(List<Coordinate> coordinates) {
        this.start = coordinates.get(0);
        this.end = coordinates.get(1);
    }

    public HorizontalLineSegment(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public HorizontalLineSegment createCommonLineSegmentIfOverlapping(HorizontalLineSegment lineSegment) {
        if (!isOverlapping(lineSegment)) {
            return null;
        } else {
            int min = Math.min(getStart().getX(), lineSegment.getStart().getX());
            int max = Math.max(getEnd().getX(), lineSegment.getEnd().getX());
            return new HorizontalLineSegment(new Coordinate(min, getStart().getY()), new Coordinate(max, getStart().getY()));
        }
    }

    public boolean isOverlapping(HorizontalLineSegment o) {
        return (getStart().getX() >= o.getStart().getX() && o.getEnd().getX() >= getStart().getX()) ||
               (getStart().getX() <= o.getStart().getX() && o.getStart().getX() <= getEnd().getX());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorizontalLineSegment)) return false;
        HorizontalLineSegment that = (HorizontalLineSegment) o;
        return Objects.equals(getStart(), that.getStart()) && Objects.equals(getEnd(), that.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }

    @Override
    public int compareTo(HorizontalLineSegment o) {
        return Coordinate.getXOrderComparator().compare(start, o.getStart());
    }

    @Override
    public String toString() {
        return "L[" + start + ", " + end + "]";
    }

    public HorizontalLineSegment reCalculateFirst(int from) {
        return new HorizontalLineSegment(new Coordinate(from, getStart().getY()), getEnd());
    }

    public HorizontalLineSegment reCalculateLast(int to) {
        return new HorizontalLineSegment(getStart(), new Coordinate(to, getEnd().getY()));
    }

}
