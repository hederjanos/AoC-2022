package day._8;

import util.coordinate.Coordinate;
import util.coordinate.Direction;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.List;
import java.util.function.Function;

public class TreeHeightMap extends IntegerGrid {

    protected TreeHeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
    }

    public int getNumberOfVisibleTrees() {
        return (int) board.stream().map(this::isVisible).filter(visible -> visible).count();
    }

    private boolean isVisible(GridCell<Integer> gridCell) {
        boolean isVisible = false;
        if (isOnEdge(gridCell)) {
            isVisible = true;
        } else {
            Direction[] directions = Direction.values();
            for (Direction direction : directions) {
                if (direction.ordinal() % 2 == 0) {
                    isVisible = isGridCellVisibleFromThat(gridCell, direction);
                    if (isVisible) {
                        break;
                    }
                }
            }
        }
        return isVisible;
    }

    private boolean isOnEdge(GridCell<Integer> gridCell) {
        Coordinate coordinate = gridCell.getCoordinate();
        return coordinate.getX() == 0 || coordinate.getX() == height - 1 ||
               coordinate.getY() == 0 || coordinate.getY() == width - 1;
    }

    private boolean isGridCellVisibleFromThat(GridCell<Integer> gridCell, Direction direction) {
        boolean gridCellIsVisibleFromThatDirection = true;
        Coordinate newCoordinate = gridCell.getCoordinate();
        do {
            if (newCoordinate != gridCell.getCoordinate()) {
                GridCell<Integer> cellInLine = board.get(calculateCellIndex(newCoordinate.getX(), newCoordinate.getY()));
                if (cellInLine.getValue() >= gridCell.getValue()) {
                    gridCellIsVisibleFromThatDirection = false;
                    break;
                }
            }
            newCoordinate = newCoordinate.copy();
            newCoordinate.setX(newCoordinate.getX() + direction.getX());
            newCoordinate.setY(newCoordinate.getY() + direction.getY());
        } while (isCoordinateInBounds(newCoordinate));
        return gridCellIsVisibleFromThatDirection;
    }

}
