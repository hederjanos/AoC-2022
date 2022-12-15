package day._15;

import util.common.Solver;

public class BeaconExclusionZoneSolver extends Solver<Long> {

    private final TunnelNetwork tunnelNetwork;

    public BeaconExclusionZoneSolver(String filename) {
        super(filename);
        tunnelNetwork = new TunnelNetwork(puzzle);
    }

    @Override
    protected Long solvePartOne() {
        return tunnelNetwork.calculatePositionsNotContainingBeaconAtHorizontal(2000000);
    }

    @Override
    protected Long solvePartTwo() {
//        for (int i = 0; i <= 4_000_000; i++) {
//
//            int fuckIt = tunnelNetwork.fuckIt(i, 0, 4000000);
//            if (fuckIt != 4_000_000) {
//                System.out.println(i);
//                System.out.println(fuckIt);
//            }
//            tunnelNetwork.fuckIt(3363767, 0, 4000000);
//        }
        long i = 3157535L * 4000000 + 3363767;
        tunnelNetwork.fuckIt(3363767, 0, 4000000);
        System.out.println(i);
        return null;
    }

}
