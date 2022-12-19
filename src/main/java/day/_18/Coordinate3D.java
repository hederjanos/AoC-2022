package day._18;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coordinate3D {

    private int x;
    private int y;
    private int z;

    public Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate3D(Coordinate3D coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException();
        }
        setX(coordinate.getX());
        setY(coordinate.getY());
        setZ(coordinate.getZ());
    }

    public Coordinate3D(String coordinate) {
        String[] input = coordinate.split(",");
        x = Integer.parseInt(input[0]);
        y = Integer.parseInt(input[1]);
        z = Integer.parseInt(input[2]);
    }

    public Set<Coordinate3D> getSideCoordinates() {
        Set<Coordinate3D> sideCoordinates = new HashSet<>();
        Coordinate3D sideCoordinate;
        ReducedDirection3D[] directions = ReducedDirection3D.values();
        for (ReducedDirection3D direction : directions) {
            sideCoordinate = copy();
            sideCoordinate.setX(sideCoordinate.getX() + direction.getX());
            sideCoordinate.setY(sideCoordinate.getY() + direction.getY());
            sideCoordinate.setZ(sideCoordinate.getZ() + direction.getZ());
            sideCoordinates.add(sideCoordinate);
        }
        return sideCoordinates;
    }

    public Coordinate3D copy() {
        return new Coordinate3D(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate3D)) return false;
        Coordinate3D that = (Coordinate3D) o;
        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }
    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

}
