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
       return costs;
    }

    @Override
    public String toString() {
        return "Blueprint{" +
               "id=" + id +
               ", costs=" + Arrays.deepToString(costs) +
               '}';
    }

}
