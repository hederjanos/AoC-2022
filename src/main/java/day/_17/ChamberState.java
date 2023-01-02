package day._17;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChamberState {

    private final List<Integer> heightDifferences;

    public ChamberState(List<Integer> heightDifferences) {
        this.heightDifferences = heightDifferences;
    }

    public List<Integer> getHeightDifferences() {
        return new ArrayList<>(heightDifferences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChamberState)) return false;
        ChamberState that = (ChamberState) o;
        return Objects.equals(getHeightDifferences(), that.getHeightDifferences());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeightDifferences());
    }

    @Override
    public String toString() {
        return heightDifferences.toString();
    }
}
