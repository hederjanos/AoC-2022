package day._19;

import java.util.Arrays;

public class Blueprint {

    private final int id;
    private final int[][] costs;

    public Blueprint(int id, int[][] costs) {
        this.id = id;
        this.costs = costs;
    }

    public int getId() {
        return id;
    }

    public int[][] getCosts() {
        return copyCosts();
    }

    private int[][] copyCosts() {
        int n = costs.length;
        int[][] copyOfArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(costs[i], 0, copyOfArray[i], 0, n);
        }
        return copyOfArray;
    }

    @Override
    public String toString() {
        return "Blueprint{" +
               "id=" + id +
               ", costs=" + Arrays.deepToString(costs) +
               '}';
    }

}
