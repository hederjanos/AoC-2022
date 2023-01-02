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
                .map(Packet::parseAPacket)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return IntStream.iterate(0, i -> i < packets.size() - 2, i -> i + 2)
                .map(i -> packets.get(i).compareTo(packets.get(i + 1)) <= 0 ? i / 2 + 1 : 0)
                .sum();
    }

    @Override
    protected Integer solvePartTwo() {
        Packet firstDivider = Packet.parseAPacket("[[2]]");
        Packet secondDivider = Packet.parseAPacket("[[6]]");
        int lowerThanFirstDivider = 0;
        int greaterThanSecondDivider = 0;
        for (Packet packet : packets) {
            if (packet.compareTo(firstDivider) < 0) {
                lowerThanFirstDivider++;
            }
            if ((packet.compareTo(secondDivider) < 0)) {
                greaterThanSecondDivider++;
            }
        }
        return (lowerThanFirstDivider + 1) * (greaterThanSecondDivider + 2);
    }

}
