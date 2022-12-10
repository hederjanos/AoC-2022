package day._10;

import util.common.Solver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CathodeRayTubeSolver extends Solver<Integer> {

    public CathodeRayTubeSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {

        AtomicInteger cycleCounter = new AtomicInteger(1);
        AtomicInteger register = new AtomicInteger(1);
        AtomicInteger read = new AtomicInteger(0);

        return IntStream.iterate(20, i -> i <= 220, i -> i + 40)
                .boxed()
                .map(i -> {
                    completeCycles(cycleCounter, i, register, read);
                    return i * register.get();
                })
                .reduce(0, Integer::sum);

    }

    private boolean completeCycles(AtomicInteger cycleCounter, Integer i, AtomicInteger register, AtomicInteger read) {
        boolean completed = true;
        while (cycleCounter.get() != i) {
            String[] command = puzzle.get(read.get()).split(" ");
            if (command[0].equals("noop")) {
                cycleCounter.incrementAndGet();
                read.incrementAndGet();
            } else {
                if (cycleCounter.get() + 1 == i) {
                    completed = false;
                    break;
                } else {
                    cycleCounter.incrementAndGet();
                }
                cycleCounter.incrementAndGet();
                read.incrementAndGet();
                register.addAndGet(Integer.parseInt(command[1]));
            }
        }
        return completed;
    }


    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
