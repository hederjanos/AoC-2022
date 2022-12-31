package day._19;

import util.common.Solver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NotEnoughMineralsSolver extends Solver<Integer> {

    private final List<Blueprint> blueprints;
    private final Factory factory;

    public NotEnoughMineralsSolver(String filename) {
        super(filename);
        blueprints = puzzle.stream().map(this::parseABlueprint).collect(Collectors.toList());
        factory = new Factory();
    }

    private Blueprint parseABlueprint(String line) {
        int id = 0;
        int[][] costs = new int[4][4];
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(line);
        int i = 0;
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            if (i == 0) {
                id = number;
            } else if (i == 1 || i == 2 || i == 3) {
                costs[i - 1][0] = number;
            } else if (i == 4) {
                costs[i - 2][1] = number;
            } else if (i == 5) {
                costs[i - 2][0] = number;
            } else {
                costs[i - 3][2] = number;
            }
            i++;
        }
        return new Blueprint(id, costs);
    }

    @Override
    protected Integer solvePartOne() {
        return blueprints.stream()
                .map(blueprint -> factory.produce(blueprint, 24) * blueprint.getId())
                .reduce(0, Integer::sum);
    }

    @Override
    protected Integer solvePartTwo() {
        return blueprints.stream()
                .limit(3)
                .map(blueprint -> factory.produce(blueprint, 32))
                .reduce(1, (a, b) -> a * b);
    }

}
