package day._16;

import java.util.Objects;

public class Valve {

    private final String label;
    private final int flowRate;

    public Valve(String label, int flowRate) {
        this.label = label;
        this.flowRate = flowRate;
    }

    public String getLabel() {
        return label;
    }

    public int getFlowRate() {
        return flowRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return Objects.equals(label, valve.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

}
