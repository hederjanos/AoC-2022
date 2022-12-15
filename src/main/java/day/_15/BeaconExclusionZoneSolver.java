package day._15;

import util.common.Solver;

public class BeaconExclusionZoneSolver extends Solver<Integer> {

    private TunnelNetwork tunnelNetwork;

    public BeaconExclusionZoneSolver(String filename) {
        super(filename);
        tunnelNetwork = new TunnelNetwork(puzzle);
    }

    @Override
    protected Integer solvePartOne() {
        return tunnelNetwork.calculatePositionsNotContainingBeaconAtHorizontal(2000000);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
