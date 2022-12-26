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

    public Optional<Expedition> findShortestExpedition() {
        Optional<Expedition> result = Optional.empty();
        Set<Expedition> expeditionsInProgress = new HashSet<>();
        Expedition startExpedition = new Expedition(this.start, 0);
        Deque<Expedition> expeditions = new ArrayDeque<>();
        expeditions.offer(startExpedition);
        expeditionsInProgress.add(startExpedition);
        while (!expeditions.isEmpty()) {
            Expedition currentExpedition = expeditions.poll();
            if (target.equals(currentExpedition.getCoordinate())) {
                result = Optional.of(currentExpedition);
                break;
            }
            if (mustWait(expeditions, currentExpedition, expeditionsInProgress)) {
                Expedition wait = new Expedition(currentExpedition.getCoordinate(), currentExpedition.getElapsedTime() + 1);
                if (!expeditionsInProgress.contains(wait)) {
                    expeditions.offer(wait);
                    expeditionsInProgress.add(wait);
                }
            }
        }
        return result;
    }

    private boolean mustWait(Deque<Expedition> expeditions, Expedition currentExpedition, Set<Expedition> expeditionsInProgress) {
        boolean mustWait = true;
        for (Coordinate neighbour : currentExpedition.getCoordinate().getOrthogonalAdjacentCoordinates()) {
            if (isCoordinateInBounds(neighbour) || neighbour.equals(target)) {
                int elapsedTime = currentExpedition.getElapsedTime() + 1;
                Expedition expedition = new Expedition(neighbour, elapsedTime);
                if (!expeditionsInProgress.contains(expedition)) {
                    //TODO maybe blizzard states can be cached
                    Set<Coordinate> currentBlizzards = moveBlizzards(elapsedTime).stream()
                            .map(Blizzard::getCoordinate)
                            .collect(Collectors.toSet());
                    if (!currentBlizzards.contains(neighbour)) {
                        mustWait = false;
                        expeditions.offer(expedition);
                        expeditionsInProgress.add(expedition);
                    }
                }
            }
        }
        return mustWait;
    }

    public Set<Blizzard> moveBlizzards(int elapsedTime) {
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

    public String toString(Set<Blizzard> blizzards) {
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
                                symbol = getBlizzardSymbolOrNothing(blizzards, coordinate);
                            }
                            return symbol;
                        })
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getBlizzardSymbolOrNothing(Set<Blizzard> blizzards, Coordinate coordinate) {
        String symbol;
        Set<Blizzard> matches = blizzards.stream()
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

}
