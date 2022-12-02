package day._2;

public enum GameState {

    LOSE(0),
    DRAW(3),
    WIN(6);

    private final int value;

    public int getValue() {
        return value;
    }

    GameState(int value) {
        this.value = value;
    }

}
