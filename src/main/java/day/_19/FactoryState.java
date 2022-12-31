package day._19;

public class FactoryState {

    private final int elapsedTime;
    private final int[] inventory;
    private final int[] bots;

    public FactoryState(int elapsedTime, int[] inventory, int[] bots) {
        this.elapsedTime = elapsedTime;
        this.inventory = inventory;
        this.bots = bots;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int[] getInventory() {
        return inventory;
    }

    public int[] getBots() {
        return bots;
    }

}
