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

    public Chamber(List<Direction> jetPattern) {
        currentRock = instantiateRock();
        this.jetPattern = jetPattern;
    }

    private Rock instantiateRock() {
        Rock rock;
        try {
            Constructor<?> constructor = ROCK_CLASSES.get((int) rockCounter).getConstructor(Coordinate.class);
            int initY = getHeightOfFallenRocks();
            rock = (Rock) constructor.newInstance(new Coordinate(START_X, initY - START_DIFF));
            return rock;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getHeightOfFallenRocks() {
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
        return -1L * (getHeightOfFallenRocks()) + 1;
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

}
