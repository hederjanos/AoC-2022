package day._22;

public class Instruction {

    private final int numberOfMoves;
    private final Turn turn;

    public Instruction(String instruction) {
        numberOfMoves = Integer.parseInt(instruction.substring(0, instruction.length() - 1));
        turn = Turn.valueOf(instruction.charAt(instruction.length() - 1));
    }

    public Instruction(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
        this.turn = null;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public Turn getTurn() {
        return turn;
    }
}
