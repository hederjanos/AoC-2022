package day._24;

import util.coordinate.Coordinate;
import util.coordinate.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BlizzardBasin {

    private final int width;
    private final int height;
    private final Coordinate start;
    private final Coordinate target;
    private final Set<Blizzard> blizzards;
    private final TreeMap<Integer, Set<Blizzard>> blizzardsStates = new TreeMap<>();
    private final TreeMap<Integer, Set<Coordinate>> occupiedPositions = new TreeMap<>();

    public BlizzardBasin(List<String> puzzle) {
        width = puzzle.get(0).length();
        height = puzzle.size();
        start = new Coordinate(1, 0);
        target = new Coordinate(width - 2, height - 1);
        blizzards = IntStream.range(0, puzzle.size())
                .mapToObj(i -> {
                    String line = puzzle.get(i);
                    return IntStream.range(0, line.length())
                            .filter(j -> line.charAt(j) != '#' && line.charAt(j) != '.')
                            .mapToObj(j -> parseBlizzard(i, j, line))
                            .collect(Collectors.toSet());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Blizzard parseBlizzard(int i, int j, String line) {
        Direction direction;
        switch (line.charAt(j)) {
            case '>':
                direction = Direction.RIGHT;
                break;
            case '<':
                direction = Direction.LEFT;
                break;
            case 'v':
                direction = Direction.DOWN;
                break;
            case '^':
                direction = Direction.UP;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return new Blizzard(new Coordinate(j, i), direction);
    }

    public Optional<Expedition> findShortestExpedition(Expedition from, Coordinate to) {
        Optional<Expedition> result = Optional.empty();
        Set<Expedition> expeditionsInProgress = new HashSet<>();
        refreshBlizzardsStates(from.getElapsedTime());
        Deque<Expedition> expeditions = new ArrayDeque<>();
        expeditions.offer(from);
        expeditionsInProgress.add(from);
        while (!expeditions.isEmpty()) {
            Expedition currentExpedition = expeditions.poll();
            Set<Coordinate> currentBlizzards;
            if (!occupiedPositions.containsKey(currentExpedition.getElapsedTime())) {
                Integer lastKey = occupiedPositions.lastKey();
                while (lastKey < currentExpedition.getElapsedTime()) {
                    refreshBlizzardsStates(++lastKey);
                }
            }
            currentBlizzards = occupiedPositions.get(currentExpedition.getElapsedTime());
            if (!currentBlizzards.contains(currentExpedition.getCoordinate())) {
                if (to.equals(currentExpedition.getCoordinate())) {
                    result = Optional.of(currentExpedition);
                    break;
                }
                discoverNextPositions(currentExpedition, to, expeditions, expeditionsInProgress);
                Expedition wait = new Expedition(currentExpedition.getCoordinate(), currentExpedition.getElapsedTime() + 1);
                if (!expeditionsInProgress.contains(wait)) {
                    expeditions.offer(wait);
                    expeditionsInProgress.add(wait);
                }
            }
        }
        return result;
    }

    private void refreshBlizzardsStates(int i) {
        blizzardsStates.put(i, moveBlizzards(i));
        occupiedPositions.put(i, blizzardsStates.get(i).stream().map(Blizzard::getCoordinate).collect(Collectors.toSet()));
    }

    private void discoverNextPositions(Expedition currentExpedition, Coordinate to, Deque<Expedition> expeditions, Set<Expedition> expeditionsInProgress) {
        for (Coordinate neighbour : currentExpedition.getCoordinate().getOrthogonalAdjacentCoordinates()) {
            if (to.equals(neighbour) || isCoordinateInBounds(neighbour)) {
                Expedition expedition = new Expedition(neighbour, currentExpedition.getElapsedTime() + 1);
                if (!expeditionsInProgress.contains(expedition)) {
                    expeditions.offer(expedition);
                    expeditionsInProgress.add(expedition);
                }
            }
        }
    }

    private Set<Blizzard> moveBlizzards(int elapsedTime) {
        return blizzards.stream()
                .map(blizzard -> moveBlizzard(elapsedTime, blizzard))
                .collect(Collectors.toSet());
    }

    private Blizzard moveBlizzard(int elapsedTime, Blizzard blizzard) {
        Direction direction = blizzard.getDirection();
        if (Direction.UP.equals(direction) || Direction.DOWN.equals(direction)) {
            return moveVertically(elapsedTime, blizzard, direction);
        } else {
            return moveHorizontally(elapsedTime, blizzard, direction);
        }
    }

    private Blizzard moveVertically(int elapsedTime, Blizzard blizzard, Direction direction) {
        int newY;
        int modHeight = height - 2;
        int remainder = elapsedTime % modHeight;
        if (Direction.DOWN.equals(direction)) {
            newY = blizzard.getCoordinate().getY() + remainder;
            if (newY > modHeight) {
                newY = newY - modHeight;
            }
        } else {
            newY = blizzard.getCoordinate().getY() - remainder;
            if (newY <= 0) {
                newY = newY + modHeight;
            }
        }
        return new Blizzard(new Coordinate(blizzard.getCoordinate().getX(), newY), blizzard.getDirection());
    }

    private Blizzard moveHorizontally(int elapsedTime, Blizzard blizzard, Direction direction) {
        int newX;
        int modWidth = width - 2;
        int remainder = elapsedTime % modWidth;
        if (Direction.RIGHT.equals(direction)) {
            newX = blizzard.getCoordinate().getX() + remainder;
            if (newX > modWidth) {
                newX = newX - modWidth;
            }
        } else {
            newX = blizzard.getCoordinate().getX() - remainder;
            if (newX <= 0) {
                newX = newX + modWidth;
            }
        }
        return new Blizzard(new Coordinate(newX, blizzard.getCoordinate().getY()), blizzard.getDirection());
    }

    private boolean isCoordinateInBounds(Coordinate coordinate) {
        return coordinate.getX() < width - 1 && coordinate.getX() > 0 && coordinate.getY() < height - 1 && coordinate.getY() > 0;
    }

    public String toString(int elapsedTime) {
        return IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, width)
                        .mapToObj(j -> {
                            String symbol = "";
                            Coordinate coordinate = new Coordinate(j, i);
                            if (start.equals(coordinate) || target.equals(coordinate)) {
                                symbol = ".";
                            } else if (!isCoordinateInBounds(coordinate)) {
                                symbol = "#";
                            } else {
                                symbol = getBlizzardSymbolOrNothing(elapsedTime, coordinate);
                            }
                            return symbol;
                        })
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getBlizzardSymbolOrNothing(int elapsedTime, Coordinate coordinate) {
        String symbol;
        Set<Blizzard> matches = blizzardsStates.get(elapsedTime).stream()
                .filter(blizzard -> blizzard.getCoordinate().equals(coordinate))
                .collect(Collectors.toSet());
        if (matches.size() == 1) {
            Blizzard blizzard = matches.stream().findFirst().orElseThrow();
            switch (blizzard.getDirection()) {
                case RIGHT:
                    symbol = ">";
                    break;
                case LEFT:
                    symbol = "<";
                    break;
                case UP:
                    symbol = "^";
                    break;
                default:
                    symbol = "v";
                    break;
            }
        } else if (matches.size() > 1) {
            symbol = matches.size() + "";
        } else {
            symbol = ".";
        }
        return symbol;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getTarget() {
        return target;
    }
}
