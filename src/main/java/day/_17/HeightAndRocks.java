package day._17;

public class HeightAndRocks {

    private final int height;
    private final int numberOfRocks;

    public HeightAndRocks(int height, int numberOfRocks) {
        this.height = height;
        this.numberOfRocks = numberOfRocks;
    }

    public int getHeight() {
        return height;
    }

    public int getNumberOfRocks() {
        return numberOfRocks;
    }

    @Override
    public String toString() {
        return "{" + height + ", " + numberOfRocks + "}";
    }

}
