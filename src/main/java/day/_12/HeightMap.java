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
                                    cell.setValue((int) 'a');
                                    start = cell;
                                }
                                if ('E' == cell.getValue()) {
                                    cell.setValue((int) 'z');
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
        Set<GridCell<Integer>> visitedCells = new HashSet<>();
        PathCell solution = new PathCell(startCell.getCoordinate(), 0);
        Deque<PathCell> pathCells = new ArrayDeque<>();
        pathCells.offer(solution);
        while (!pathCells.isEmpty()) {
            PathCell currentPath = pathCells.poll();
            Coordinate currentCoordinate = currentPath.getCoordinate();
            GridCell<Integer> currentCell = board.get(calculateCellIndex(currentCoordinate.getX(), currentCoordinate.getY()));
            if (target.equals(currentCell)) {
                solution = currentPath;
                break;
            }
            for (Coordinate neighbour : currentCoordinate.getOrthogonalAdjacentCoordinates()) {
                if (isCoordinateInBounds(neighbour)) {
                    GridCell<Integer> nextCell = board.get(calculateCellIndex(neighbour.getX(), neighbour.getY()));
                    Integer currentSteps = currentPath.getNumberOfSteps();
                    if (!visitedCells.contains(nextCell) && isElevationInRange(currentCell, nextCell)) {
                        pathCells.offer(new PathCell(nextCell.getCoordinate(), ++currentSteps));
                        visitedCells.add(nextCell);
                    }
                }
            }
        }
        return solution;
    }

    private boolean isElevationInRange(GridCell<Integer> currentCell, GridCell<Integer> nextCell) {
        int elevation = nextCell.getValue() - currentCell.getValue();
        return elevation <= 1;
    }

    public List<GridCell<Integer>> collectStartCells() {
        return board.stream()
                .filter(gridCell -> gridCell.getValue() == 'a')
                .map(GridCell::copy)
                .collect(Collectors.toList());
    }

}
