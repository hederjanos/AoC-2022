package day._18;

public enum ReducedDirection3D {

    FORWARD(0, -1, 0),
    RIGHT(1, 0, 0),
    DOWNWARD(0, 1, 0),
    LEFT(-1, 0, 0),
    UP(0, 0, 1),
    DOWN(0, 0, -1);

    private final int x;
    private final int y;
    private final int z;

    ReducedDirection3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

}
