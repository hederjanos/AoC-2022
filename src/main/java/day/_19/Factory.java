package day._19;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.IntStream;

public class Factory {

    public int produce(Blueprint blueprint, int availableTime) {
        int maxGeodes = Integer.MIN_VALUE;
        int[] maxRobots = getMaxRobots(blueprint);
        FactoryState initState = new FactoryState(0, new int[]{0, 0, 0, 0}, new int[]{1, 0, 0, 0});
        Deque<FactoryState> states = new ArrayDeque<>();
        states.offer(initState);
        while (!states.isEmpty()) {
            FactoryState currentState = states.poll();
            int elapsedTime = currentState.getElapsedTime();
            int[] inventory = currentState.getInventory();
            int[] bots = currentState.getBots();
            int[][] costs = blueprint.getCosts();
            for (int i = 0; i < costs.length; i++) {
                if (bots[i] == maxRobots[i]) {
                    continue;
                }
                int[] cost = costs[i];
                int waitingTime = calculateWaitingTime(availableTime, inventory, bots, cost);
                int newElapsedTime = elapsedTime + waitingTime + 1;
                if (newElapsedTime >= availableTime) {
                    continue;
                }
                int[] newInventory = new int[inventory.length];
                for (int k = 0; k < bots.length; k++) {
                    newInventory[k] = inventory[k] + bots[k] * (waitingTime + 1) - cost[k];
                }
                int[] newBots = new int[bots.length];
                System.arraycopy(bots, 0, newBots, 0, bots.length);
                newBots[i] += 1;
                int remainingTime = availableTime - newElapsedTime;
                if (((remainingTime - 1) * remainingTime) / 2 + newInventory[3] + remainingTime * newBots[3] < maxGeodes) {
                    continue;
                }
                states.offer(new FactoryState(newElapsedTime, newInventory, newBots));
            }
            maxGeodes = Math.max(maxGeodes, inventory[3] + bots[3] * (availableTime - elapsedTime));
        }
        return maxGeodes;
    }

    private int[] getMaxRobots(Blueprint blueprint) {
        int[] maxRobots = new int[blueprint.getCosts().length];
        IntStream.range(0, blueprint.getCosts().length - 1)
                .forEach(i -> maxRobots[i] = Arrays.stream(blueprint.getCosts())
                        .map(cost -> cost[i]).max(Integer::compareTo)
                        .orElseThrow());
        maxRobots[maxRobots.length - 1] = Integer.MAX_VALUE;
        return maxRobots;
    }

    private int calculateWaitingTime(int availableTime, int[] inventory, int[] bots, int[] cost) {
        return IntStream.range(0, cost.length - 1)
                .mapToObj(j -> {
                    if (cost[j] <= inventory[j]) {
                        return 0;
                    }
                    if (bots[j] == 0) {
                        return availableTime + 1;
                    }
                    return (cost[j] - inventory[j] + bots[j] - 1) / bots[j];
                })
                .max(Integer::compareTo)
                .orElseThrow();
    }


}
