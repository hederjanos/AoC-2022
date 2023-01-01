package day._22;

public class Instruction {

    private final Turn turn;
    private final int numberOfMoves;

    public Instruction(String instruction) {
        numberOfMoves = Integer.parseInt(instruction.substring(0, instruction.length() -1));
        turn = Turn.valueOf(instruction.charAt(instruction.length() -1));
    }

    public Turn getTurn() {
        return turn;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }
}
