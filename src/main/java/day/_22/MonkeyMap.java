package day._22;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonkeyMap {

    private Coordinate start;
    private Explorer explorer;
    private final Set<Coordinate> walls = new HashSet<>();
    private final Set<Coordinate> tiles = new HashSet<>();
    private static final int[] FACING_VALUES = new int[]{3, 0, 0, 0, 1, 0, 2, 0};

    public MonkeyMap(List<String> stringMap) {
        for (int i = 0; i < stringMap.size(); i++) {
            String line = stringMap.get(i);
            for (int j = 0; j < line.length(); j++) {
                parseACoordinate(line, i, j);
            }
        }
    }

    private void parseACoordinate(String line, int i, int j) {
        Coordinate coordinate = new Coordinate(j + 1, i + 1);
        char charAt = line.charAt(j);
        if (charAt == '#') {
            walls.add(coordinate);
        } else if (charAt == '.') {
            if (explorer == null) {
                start = coordinate;
                resetExplorer();
            }
            tiles.add(coordinate);
        }
    }

    public void resetExplorer() {
        explorer = new Explorer(start, Direction.RIGHT);
    }

    public int explore(List<Instruction> instructions, boolean inThreeDimension) {
        instructions.forEach(instruction -> moveExplorer(instruction, inThreeDimension));
        Coordinate coordinate = explorer.getCoordinate();
        return 4 * coordinate.getX() + 1000 * coordinate.getY() + FACING_VALUES[explorer.getDirection().ordinal()];
    }

    private void moveExplorer(Instruction instruction, boolean inThreeDimension) {
        boolean isMoved;
        int numberOfMoves = 0;
        do {
            isMoved = explorer.move(walls, tiles, inThreeDimension);
            numberOfMoves++;
        } while (isMoved && numberOfMoves < instruction.getNumberOfMoves());
        if (Turn.R.equals(instruction.getTurn())) {
            explorer.turnRight();
        } else if (Turn.L.equals(instruction.getTurn())) {
            explorer.turnLeft();
        }
    }

}
