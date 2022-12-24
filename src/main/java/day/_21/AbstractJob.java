package day._21;

import java.util.Objects;

public abstract class AbstractJob {

    protected String label;
    protected long value;

    protected AbstractJob(String label, long value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractJob)) return false;
        AbstractJob that = (AbstractJob) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    public String getLabel() {
        return label;
    }

    public long getValue() {
        return value;
    }

    abstract AbstractJob copy();

}
