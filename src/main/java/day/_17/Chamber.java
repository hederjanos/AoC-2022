package day._17;

import day._17.rock.*;
import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chamber {

    private static final int WIDTH = 7;
    private static final int START_X = 2;
    private static final int START_DIFF = 3;
    private static final List<Class<? extends Rock>> ROCK_CLASSES = List.of(
            HorizontalRock.class,
            CrossRock.class,
            LRock.class,
            VerticalRock.class,
            SquareRock.class);
    private long numberOfTotalRocks;
    private long rockCounter;
    private Rock currentRock;
    private final Set<Coordinate> fallenRocks = new HashSet<>();
    private final List<Direction> jetPattern;
    private final Map<ChamberState, HeightAndRocks> chamberStateMap = new HashMap<>();
    private final List<ComplexChamberState> flow = new ArrayList<>();

    public Chamber(List<Direction> jetPattern) {
        currentRock = instantiateRock();
        this.jetPattern = jetPattern;
    }

    private Rock instantiateRock() {
        Rock rock;
        try {
            Constructor<?> constructor = ROCK_CLASSES.get((int) rockCounter).getConstructor(Coordinate.class);
            int initY = getMinHeightOfFallenRocks();
            rock = (Rock) constructor.newInstance(new Coordinate(START_X, initY - START_DIFF));
            return rock;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getMinHeightOfFallenRocks() {
        if (fallenRocks.isEmpty()) {
            return 1;
        }
        return fallenRocks.stream().min(Comparator.comparingInt(Coordinate::getY)).map(Coordinate::getY).orElseThrow();
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

    public boolean move(Direction direction) {
        boolean rockIsMoving = true;
        Rock newRock = currentRock;
        try {
            newRock = currentRock.getClass().getConstructor(Rock.class).newInstance(currentRock);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        newRock.moveByDirection(direction);
        if (isRockHitFloor(newRock) || isRockHitFallenRocks(newRock) || isRockHitWall(newRock)) {
            rockIsMoving = false;
        } else {
            currentRock = newRock;
        }
        return rockIsMoving;
    }

    public boolean moveDown() {
        boolean rockIsMoving = true;
        Rock newRock = currentRock;
        try {
            newRock = currentRock.getClass().getConstructor(Rock.class).newInstance(currentRock);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        newRock.moveByDirection(Direction.DOWN);
        if (isRockHitFallenRocks(newRock) || isRockHitFloor(newRock)) {
            addRockToFallenRocks(currentRock);
            updateStates();
            currentRock = instantiateRock();
            rockIsMoving = false;
        } else {
            currentRock = newRock;
        }
        return rockIsMoving;
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
        rockCounter++;
        rockCounter %= ROCK_CLASSES.size();
    }

    private void updateStates() {
        int heightOfFallenRocks = (int) getHeightOfFallenRocks();
        List<Integer> heightDifferences = getHeightDifferences(heightOfFallenRocks);
        ChamberState chamberState = new ChamberState(heightDifferences);
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
                        .min(Comparator.comparingInt(Coordinate::getY))
                        .map(Coordinate::getY)
                        .map(maxHeightAtCol -> maxHeightAtCol + height)
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
                .min(Comparator.comparingInt(Coordinate::getY))
                .map(Coordinate::getY)
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
        return flow;
    }

}
