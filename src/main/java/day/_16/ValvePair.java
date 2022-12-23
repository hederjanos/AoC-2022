package day._16;

import java.util.Objects;

public class ValvePair {

    private final Valve from;
    private final Valve to;

    public ValvePair(Valve from, Valve to) {
        this.from = from;
        this.to = to;
    }

    public Valve getFrom() {
        return from;
    }

    public Valve getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValvePair)) return false;
        ValvePair valvePair = (ValvePair) o;
        return Objects.equals(getFrom(), valvePair.getFrom()) && Objects.equals(getTo(), valvePair.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

}
