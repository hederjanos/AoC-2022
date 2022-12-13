package day._13;

import util.common.Solver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DistressSignalSolver extends Solver<Integer> {

    private final List<Packet> packets;

    public DistressSignalSolver(String filename) {
        super(filename);
        packets = puzzle.stream()
                .filter(line -> !line.isEmpty())
                .map(Packet::parseNumber)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return IntStream.iterate(0, i -> i < packets.size() - 2, i -> i + 2)
                .map(i -> packets.get(i).compareTo(packets.get(i + 1)) <= 0 ? i / 2 + 1 : 0)
                .reduce(0, Integer::sum);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
