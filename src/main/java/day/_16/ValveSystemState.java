package day._16;

import java.util.Set;

public class ValveSystemState {
    
    private final Valve valve;
    private final int elapsedTimeInMinute;
    private final int pressurePerMinute;
    private final int releasedPressure;
    public Set<Valve> openValves;

    public ValveSystemState(Valve valve, int elapsedTimeInMinute, int pressurePerMinute, int releasedPressure, Set<Valve> openValves) {
        this.valve = valve;
        this.elapsedTimeInMinute = elapsedTimeInMinute;
        this.pressurePerMinute = pressurePerMinute;
        this.releasedPressure = releasedPressure;
        this.openValves = openValves;
    }

    public Valve getValve() {
        return valve;
    }

    public int getElapsedTimeInMinute() {
        return elapsedTimeInMinute;
    }

    public int getPressurePerMinute() {
        return pressurePerMinute;
    }

    public int getReleasedPressure() {
        return releasedPressure;
    }

    public Set<Valve> getOpenValves() {
        return openValves;
    }

}



