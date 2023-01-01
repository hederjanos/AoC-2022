package day._22;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyMapSolver extends Solver<Integer> {

    private final MonkeyMap monkeyMap;
    private final List<Instruction> instructions;

    public MonkeyMapSolver(String filename) {
        super(filename);
        monkeyMap = new MonkeyMap(puzzle.subList(0, puzzle.size() - 2));
        instructions = parseInstructions();
    }

    private List<Instruction> parseInstructions() {
        List<Instruction> instructionList = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+[RL]");
        String line = puzzle.get(puzzle.size() - 1);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            instructionList.add(new Instruction(matcher.group()));
        }
        Instruction last = new Instruction(Character.getNumericValue(line.charAt(line.length() - 1)));
        instructionList.add(last);
        return instructionList;
    }

    @Override
    protected Integer solvePartOne() {
        return monkeyMap.explore(instructions);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
