package day._16;

public final class ValveState {

    private final Valve valve;
    private final int numberOfSteps;

    public ValveState(Valve valve, int numberOfSteps) {
        this.valve = valve;
        this.numberOfSteps = numberOfSteps;
    }

    public Valve getValve() {
        return valve;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

}
