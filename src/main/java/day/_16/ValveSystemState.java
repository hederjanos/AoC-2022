package day._16;

import java.util.Set;

public class ValveSystemState {
    
    private final Valve valve;
    private final int elapsedTime;
    private final int pressureRate;
    private final int releasedPressure;
    private final Set<Valve> openValves;

    public ValveSystemState(Valve valve, int elapsedTimeInMinute, int pressureRate, int releasedPressure, Set<Valve> openValves) {
        this.valve = valve;
        this.elapsedTime = elapsedTimeInMinute;
        this.pressureRate = pressureRate;
        this.releasedPressure = releasedPressure;
        this.openValves = openValves;
    }

    public Valve getValve() {
        return valve;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getPressureRate() {
        return pressureRate;
    }

    public int getReleasedPressure() {
        return releasedPressure;
    }

    public Set<Valve> getOpenValves() {
        return openValves;
    }

}



