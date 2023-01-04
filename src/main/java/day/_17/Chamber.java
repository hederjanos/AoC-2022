package day._17;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chamber {

    private static final int WIDTH = 7;
    private static final int START_X = 2;
    private static final int START_DIFF = 3;
    private long numberOfTotalRocks;
    private Rock currentRock;
    private final Set<Coordinate> fallenRocks = new HashSet<>();
    private final List<Direction> jetPattern;
    private final Map<ChamberState, HeightAndRocks> chamberStateMap = new HashMap<>();
    private final List<ComplexChamberState> flow = new ArrayList<>();

    public Chamber(List<Direction> jetPattern) {
        currentRock = new Rock(numberOfTotalRocks, new Coordinate(START_X, getMinHeightOfFallenRocks() - START_DIFF));
        this.jetPattern = jetPattern;
    }

    private int getMinHeightOfFallenRocks() {
        if (fallenRocks.isEmpty()) {
            return 1;
        }
        return fallenRocks.stream().mapToInt(Coordinate::getY).min().orElseThrow();
    }

    public long moveByJetPattern(long counter) {
        int i = 0;
        while (numberOfTotalRocks < counter - 1) {
            Direction direction = jetPattern.get(i % jetPattern.size());
            move(direction);
            moveDown();
            i++;
        }
        return getHeightOfFallenRocks();
    }

    private long getHeightOfFallenRocks() {
        return -1L * getMinHeightOfFallenRocks() + 1;
    }

    public void move(Direction direction) {
        Rock newRock = currentRock.moveByDirection(direction);
        if (!isRockHitFloor(newRock) && !isRockHitFallenRocks(newRock) && !isRockHitWall(newRock)) {
            currentRock = newRock;
        }
    }

    public void moveDown() {
        Rock newRock = currentRock.moveByDirection(Direction.DOWN);
        if (isRockHitFallenRocks(newRock) || isRockHitFloor(newRock)) {
            addRockToFallenRocks(currentRock);
            updateStates();
            currentRock = new Rock(numberOfTotalRocks, new Coordinate(START_X, getMinHeightOfFallenRocks() - START_DIFF));
        } else {
            currentRock = newRock;
        }
    }

    private boolean isRockHitWall(Rock rock) {
        return rock.getCoordinates().stream()
                .anyMatch(coordinate -> coordinate.getX() < 0 || coordinate.getX() >= WIDTH);
    }

    private boolean isRockHitFloor(Rock rock) {
        return rock.getCoordinates().stream()
                .anyMatch(coordinate -> coordinate.getY() > 0);
    }

    private boolean isRockHitFallenRocks(Rock rock) {
        return rock.getCoordinates().stream()
                .anyMatch(fallenRocks::contains);
    }

    private void addRockToFallenRocks(Rock rock) {
        fallenRocks.addAll(rock.getCoordinates());
        numberOfTotalRocks++;
    }

    private void updateStates() {
        int heightOfFallenRocks = (int) getHeightOfFallenRocks();
        ChamberState chamberState = new ChamberState(getHeightDifferences(heightOfFallenRocks));
        if (chamberStateMap.containsKey(chamberState)) {
            flow.add(new ComplexChamberState(chamberStateMap.get(chamberState), new HeightAndRocks(heightOfFallenRocks, (int) numberOfTotalRocks)));
        } else {
            flow.add(new ComplexChamberState(null, new HeightAndRocks(heightOfFallenRocks, (int) numberOfTotalRocks)));
            chamberStateMap.put(chamberState, new HeightAndRocks(heightOfFallenRocks, (int) numberOfTotalRocks));
        }
    }

    private List<Integer> getHeightDifferences(int height) {
        return IntStream.range(0, WIDTH)
                .mapToObj(i -> fallenRocks.stream()
                        .filter(coordinate -> coordinate.getX() == i)
                        .mapToInt(coordinate -> coordinate.getY() + height)
                        .min()
                        .orElse(0))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        int height = getMaxVerticalPositionOfCurrent();
        return IntStream.rangeClosed(0, -1 * getMaxVerticalPositionOfCurrent())
                .mapToObj(i -> IntStream.range(0, WIDTH)
                        .mapToObj(j -> getPrintAt(i + height, j))
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private int getMaxVerticalPositionOfCurrent() {
        return currentRock.getCoordinates().stream()
                .mapToInt(Coordinate::getY)
                .min()
                .orElseThrow();
    }

    private String getPrintAt(int i, int j) {
        String print;
        if (fallenRocks.contains(new Coordinate(j, i))) {
            print = "#";
        } else {
            print = currentRock.getCoordinates().contains(new Coordinate(j, i)) ? "@" : ".";
        }
        return print;
    }

    public List<ComplexChamberState> getFlow() {
        return new ArrayList<>(flow);
    }

}
