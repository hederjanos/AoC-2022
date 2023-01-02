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
                                Coordinate coordinate = new Coordinate(j, i);
                                GridCell<Integer> cell = new GridCell<>(coordinate, numbers.get(j));
                                if ('S' == cell.getValue()) {
                                    cell = new GridCell<>(coordinate, (int) 'a');
                                    start = cell;
                                }
                                if ('E' == cell.getValue()) {
                                    cell = new GridCell<>(coordinate, (int) 'z');
                                    target = cell;
                                }
                                board.add(cell);
                            });
                });
    }

    public Optional<PathCell> findFewestStepPathFromDefault() {
        return findFewestStepPathFromStart(start);
    }

    public Optional<PathCell> findFewestStepPathFromStart(GridCell<Integer> startCell) {
        Optional<PathCell> result = Optional.of(new PathCell(startCell.getCoordinate(), 0));
        Set<GridCell<Integer>> visitedCells = new HashSet<>();
        PathCell startPath = new PathCell(startCell.getCoordinate(), 0);
        Deque<PathCell> pathCells = new ArrayDeque<>();
        pathCells.offer(startPath);
        while (!pathCells.isEmpty()) {
            PathCell currentPath = pathCells.poll();
            Coordinate currentCoordinate = currentPath.getCoordinate();
            GridCell<Integer> currentCell = board.get(calculateCellIndex(currentCoordinate.getX(), currentCoordinate.getY()));
            if (target.equals(currentCell)) {
                result = Optional.of(currentPath);
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
        return result;
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
