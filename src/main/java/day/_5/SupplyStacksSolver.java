package day._5;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SupplyStacksSolver extends Solver<String> {

    private WareHouse wareHouse;
    private final List<Procedure> procedures;

    public SupplyStacksSolver(String filename) {
        super(filename);
        procedures = parseProcedures();
    }

    private void initializeWarehouse() {
        wareHouse = parseWarehouse();
    }

    private WareHouse parseWarehouse() {
        int counter = (int) puzzle.stream().takeWhile(s -> !Character.isDigit(s.charAt(1))).count();
        String stackNumbers = puzzle.get(counter).trim();
        String numberOfStacks = Character.toString(stackNumbers.charAt(stackNumbers.length() - 1));
        return new WareHouse(numberOfStacks, puzzle.subList(0, counter));
    }

    private List<Procedure> parseProcedures() {
        return puzzle.stream()
                .dropWhile(s -> !s.startsWith("move"))
                .map(this::parseACommand)
                .collect(Collectors.toList());
    }

    private Procedure parseACommand(String s) {
        List<Integer> commands = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            commands.add(Integer.parseInt(matcher.group()));
        }
        return new Procedure(commands);
    }

    @Override
    protected String solvePartOne() {
        initializeWarehouse();
        wareHouse.processProcedures(procedures);
        return wareHouse.getTopCrates();
    }

    @Override
    protected String solvePartTwo() {
        initializeWarehouse();
        wareHouse.processProceduresEnhanced(procedures);
        return wareHouse.getTopCrates();
    }

}
