package day._15;

import util.common.Solver;

public class BeaconExclusionZoneSolver extends Solver<Integer> {

    private TunnelNetwork tunnelNetwork;

    public BeaconExclusionZoneSolver(String filename) {
        super(filename);
        tunnelNetwork = new TunnelNetwork(puzzle);
        System.out.println(tunnelNetwork);
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
