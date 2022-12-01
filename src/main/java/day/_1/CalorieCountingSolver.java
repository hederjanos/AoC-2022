package day._1;

import util.common.Solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalorieCountingSolver extends Solver<Integer> {

    private final List<Integer> sortedSumCalories;

    public CalorieCountingSolver(String filename) {
        super(filename);
        sortedSumCalories = processAndSortCalories();
    }

    @Override
    public Integer solvePartOne() {
        return sortedSumCalories.get(0);
    }

    private List<Integer> processAndSortCalories() {
        List<Integer> sumCaloriesPerElves = new ArrayList<>();
        sumCaloriesPerElves.add(0);
        int i = 0;
        for (String calorie : puzzle) {
            if (calorie.isEmpty()) {
                i++;
                sumCaloriesPerElves.add(0);
            } else {
                Integer subCalorie = sumCaloriesPerElves.get(i);
                sumCaloriesPerElves.set(i, subCalorie + Integer.parseInt(calorie));
            }
        }
        sumCaloriesPerElves.sort(Collections.reverseOrder());
        return sumCaloriesPerElves;
    }

    @Override
    protected Integer solvePartTwo() {
        return sortedSumCalories.stream().limit(3).reduce(0, Integer::sum);
    }

}
