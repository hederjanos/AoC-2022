package day._22;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyMapSolver extends Solver<Integer> {

    private MonkeyMap monkeyMap;
    private List<Instruction> instructions;

    public MonkeyMapSolver(String filename) {
        super(filename);
        monkeyMap = new MonkeyMap(puzzle.subList(0, puzzle.size() - 2));
        instructions = parseInstructions();
        System.out.println("*");
    }

    private List<Instruction> parseInstructions() {
        List<Instruction> instructionList = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+[RL]");
        Matcher matcher = pattern.matcher(puzzle.get(puzzle.size() - 1));
        while (matcher.find()) {
            instructionList.add(new Instruction(matcher.group()));
        }
        return instructionList;
    }

    @Override
    protected Integer solvePartOne() {
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
