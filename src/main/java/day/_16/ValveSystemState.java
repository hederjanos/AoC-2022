package day._16;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValveSystemState implements Comparable<ValveSystemState> {

    private Valve currentValve;
    private Map<Valve, Integer> openValves;
    private int mainCounter;

    public ValveSystemState(Valve valve) {
        this.currentValve = valve;
        openValves = new HashMap<>();
    }

    public Integer getSumPressure() {
        return openValves.entrySet().stream()
                .map(entry -> entry.getKey().getFlowRate() * entry.getValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public int compareTo(ValveSystemState o) {
        return o.getSumPressure().compareTo(getSumPressure());
    }

    public Valve getCurrentValve() {
        return currentValve;
    }

    public void setCurrentValve(Valve currentValve) {
        this.currentValve = currentValve;
    }

    public void openValve(Valve valve) {
        openValves.putIfAbsent(valve, 0);
    }

    public void release() {
        openValves = openValves.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() + 1));
    }

    public Integer since() {
       return mainCounter;
    }

    private Map<Valve, Integer> getOpenValves() {
        return openValves;
    }

    private void setOpenValves(Map<Valve, Integer> openValves) {
        this.openValves = openValves;
    }

    public ValveSystemState copy() {
        ValveSystemState newState = new ValveSystemState(getCurrentValve());
        newState.setOpenValves(getOpenValves());
        newState.setMainCounter(getMainCounter());
        return newState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValveSystemState)) return false;
        ValveSystemState that = (ValveSystemState) o;
        return Objects.equals(getCurrentValve(), that.getCurrentValve());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentValve());
    }

    public boolean allValvesOpen() {
        return !openValves.isEmpty() && openValves.values().stream().noneMatch(counter -> counter == 0);
    }

    public void setMainCounter(int mainCounter) {
        this.mainCounter = mainCounter;
    }

    public int getMainCounter() {
        return mainCounter;
    }

    public void increaseMainCounter() {
        this.mainCounter++;
    }

    @Override
    public String toString() {
        return "VS{" + "v: " + currentValve + ", c: " + mainCounter
               + ", p: " + getSumPressure() + ", vs: " + getOpenValves() + "}";
    }

}
