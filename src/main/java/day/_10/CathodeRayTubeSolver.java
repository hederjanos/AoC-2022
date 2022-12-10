package day._10;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CathodeRayTubeSolver extends Solver<Integer> {

    private static final int SCREEN_WIDTH = 40;
    private static final int SCREEN_HEIGHT = 6;
    private int register;
    private final List<Integer> registerValues = new ArrayList<>();

    public CathodeRayTubeSolver(String filename) {
        super(filename);
        register = 1;
        registerValues.add(0, register);
        puzzle.forEach(this::executeInstruction);
    }

    private void executeInstruction(String line) {
        int registerIndex = registerValues.size() - 1;
        String[] command = line.split(" ");
        if (command[0].equals("noop")) {
            registerValues.add(++registerIndex, register);
        } else {
            registerValues.add(++registerIndex, register);
            registerValues.add(++registerIndex, register);
            register += Integer.parseInt(command[1]);
        }
    }

    @Override
    protected Integer solvePartOne() {
        return IntStream.iterate(20, i -> i <= 220, i -> i + SCREEN_WIDTH)
                .mapToObj(i -> i * registerValues.get(i))
                .reduce(0, Integer::sum);
    }

    @Override
    protected Integer solvePartTwo() {
        String screen = IntStream.range(0, SCREEN_HEIGHT)
                .mapToObj(i -> IntStream.range(0, SCREEN_WIDTH)
                        .mapToObj(j -> Math.abs(registerValues.get(j + i * SCREEN_WIDTH + 1) - j) <= 1 ? "#" : " ")
                        .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(screen);
        return null;
    }

}
