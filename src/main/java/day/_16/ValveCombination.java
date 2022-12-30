package day._16;

import java.util.Objects;
import java.util.Set;

public class ValveCombination {

    private final Set<Valve> valves;

    public ValveCombination(Set<Valve> valves) {
        this.valves = valves;
    }

    public Set<Valve> getValves() {
        return valves;
    }

    public boolean differsFrom(ValveCombination other) {
        return getValves().stream().noneMatch(valve -> other.getValves().contains(valve));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValveCombination)) return false;
        ValveCombination that = (ValveCombination) o;
        return Objects.equals(getValves(), that.getValves());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValves());
    }

}
