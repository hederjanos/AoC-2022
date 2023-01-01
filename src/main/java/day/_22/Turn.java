package day._22;

public enum Turn {

    R, L;

    public static Turn valueOf(char charAt) {
        Turn turn;
        switch (charAt) {
            case 'R':
                turn = Turn.R;
                break;
            case 'L':
                turn = Turn.L;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + charAt);
        }
        return turn;
    }

}
