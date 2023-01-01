package day._22;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class MonkeyMap {

    private Explorer explorer;
    private final Set<Coordinate> walls = new HashSet<>();
    private final Set<Coordinate> tiles = new HashSet<>();
    private static final int[] FACING_VALUES = new int[]{3, 0, 0, 0, 1, 0, 2, 0};

    public MonkeyMap(List<String> stringMap) {
        IntStream.range(0, stringMap.size())
                .forEach(i -> {
                    String line = stringMap.get(i);
                    IntStream.range(0, line.length())
                            .forEach(j -> {
                                        Coordinate coordinate = new Coordinate(j + 1, i + 1);
                                        char charAt = line.charAt(j);
                                        if (charAt == '#') {
                                            walls.add(coordinate);
                                        } else if (charAt == '.') {
                                            if (explorer == null) {
                                                explorer = new Explorer(coordinate, Direction.RIGHT);
                                            }
                                            tiles.add(coordinate);
                                        }
                                    }
                            );
                });
    }

    public int explore(List<Instruction> instructions) {
        instructions.forEach(this::moveExplorer);
        Coordinate coordinate = explorer.getCoordinate();
        return 4 * coordinate.getX() + 1000 * coordinate.getY() + FACING_VALUES[explorer.getDirection().ordinal()];
    }

    private void moveExplorer(Instruction instruction) {
        boolean isMoved;
        int numberOfMoves = 0;
        do {
            isMoved = explorer.move(walls, tiles);
            numberOfMoves++;
        } while (isMoved && numberOfMoves < instruction.getNumberOfMoves());
        if (Turn.R.equals(instruction.getTurn())) {
            explorer.turnRight();
        } else if (Turn.L.equals(instruction.getTurn())) {
            explorer.turnLeft();
        }
    }

}
