package day._12;

import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeightMap extends IntegerGrid {

    private GridCell<Integer> start;
    private GridCell<Integer> target;

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        fourWayDirection = true;
        height = gridLines.size();
        width = tokenizer.apply(gridLines.get(0)).size();
        board = new ArrayList<>();
        IntStream.range(0, height)
                .forEach(i -> {
                    List<Integer> numbers = new ArrayList<>(tokenizer.apply(gridLines.get(i)));
                    IntStream.range(0, width)
                            .forEach(j -> {
                                GridCell<Integer> cell = new GridCell<>(new Coordinate(j, i), numbers.get(j));
                                if ('S' == cell.getValue()) {
                                    start = cell;
                                }
                                if ('E' == cell.getValue()) {
                                    target = cell;
                                }
                                board.add(cell);
                            });
                });
    }

    public PathCell findFewestStepPathFromDefault() {
        return findFewestStepPathFromStart(start);
    }

    public PathCell findFewestStepPathFromStart(GridCell<Integer> startCell) {
        PathCell solution = new PathCell(startCell.getCoordinate(), 0);
        startCell.setMarked();
        Deque<PathCell> queue = new ArrayDeque<>();
        queue.offer(solution);
        while (!queue.isEmpty()) {
            PathCell currentPath = queue.poll();
            int position = calculateCellIndex(currentPath.getCoordinate().getX(), currentPath.getCoordinate().getY());
            GridCell<Integer> currentCell = board.get(position);
            if (atTarget(currentCell)) {
                solution = currentPath;
                break;
            }
            for (Coordinate neighbour : currentPath.getCoordinate().getOrthogonalAdjacentCoordinates()) {
                if (isCoordinateInBounds(neighbour)) {
                    GridCell<Integer> nextCell = board.get(calculateCellIndex(neighbour.getX(), neighbour.getY()));
                    Integer currentSteps = currentPath.getNumberOfSteps();
                    if (!nextCell.isMarked() && isElevationInRange(startCell, currentCell, nextCell)) {
                        queue.offer(new PathCell(nextCell.getCoordinate(), ++currentSteps));
                        nextCell.setMarked();
                    }
                }
            }
        }
        System.out.println(solution);
        return solution;
    }

    private boolean isElevationInRange(GridCell<Integer> startCell, GridCell<Integer> currentCell, GridCell<Integer> nextCell) {
        if (atStart(startCell, currentCell)) {
            return true;
        }
        int elevation = nextCell.getValue() - currentCell.getValue();
        return elevation <= 1;
    }

    private boolean atStart(GridCell<Integer> startCell, GridCell<Integer> currentCell) {
        return startCell.getCoordinate().equals(currentCell.getCoordinate());
    }

    private boolean atTarget(GridCell<Integer> nextCell) {
        return target.getCoordinate().equals(nextCell.getCoordinate());
    }

    public List<GridCell<Integer>> collectStartCells() {
        return board.stream()
                .filter(gridCell -> gridCell.getValue() == 'a' || gridCell.getValue() == 'S')
                .map(GridCell::copy)
                .collect(Collectors.toList());
    }

}
