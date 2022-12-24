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
        JobGraph copyGraph = jobGraph.copy();
        List<AbstractJob> sortedJobs = copyGraph.sort();
        return copyGraph.resolveJobs(sortedJobs);
    }

    @Override
    protected Long solvePartTwo() {
        long start = 0L;
        long end = solvePartOne();
        long mid = start;
        jobGraph.modifyRoot();
        while (end - start > 0) {
            mid = (start + end) / 2;
            JobGraph copyGraph = jobGraph.copy();
            copyGraph.setHuman(mid);
            List<AbstractJob> sortedJobs = copyGraph.sort();
            long result = copyGraph.resolveJobs(sortedJobs);
            if (result > 0) {
                start = mid - 1;
            } else if (result < 0) {
                end = mid;
            } else {
                break;
            }
        }
        return mid;
    }

}
