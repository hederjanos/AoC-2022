package day._17;

import java.util.List;
import java.util.Objects;

public class ChamberState {

    private final List<Integer> heightDifferences;

    public ChamberState(List<Integer> heightDifferences) {
        this.heightDifferences = heightDifferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChamberState)) return false;
        ChamberState that = (ChamberState) o;
        return Objects.equals(heightDifferences, that.heightDifferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heightDifferences);
    }

    @Override
    public String toString() {
        return heightDifferences.toString();
    }

}
