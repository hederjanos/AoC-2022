package day._21;

import util.common.Solver;

import java.util.List;

public class MonkeyMathSolver extends Solver<Long> {

    private final JobGraph jobGraph;

    public MonkeyMathSolver(String filename) {
        super(filename);
        jobGraph = new JobGraph(puzzle);
    }

    @Override
    protected Long solvePartOne() {
        List<AbstractJob> sortedJobs = jobGraph.sort();
        return jobGraph.resolveJobs(sortedJobs);
    }

    @Override
    protected Long solvePartTwo() {
        return null;
    }

}
