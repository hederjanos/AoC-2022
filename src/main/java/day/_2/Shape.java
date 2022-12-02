package day._2;

public enum Shape {

    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int value;

    public int getValue() {
        return value;
    }

    Shape(int value) {
        this.value = value;
    }

}
