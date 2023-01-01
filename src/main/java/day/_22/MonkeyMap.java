package day._22;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class MonkeyMap {

    private final Set<Coordinate> walls = new HashSet<>();
    private final Set<Coordinate> tiles = new HashSet<>();

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
                                            tiles.add(coordinate);
                                        }
                                    }
                            );
                });
    }

}
