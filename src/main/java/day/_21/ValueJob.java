package day._21;

public class ValueJob extends AbstractJob {

    public ValueJob(String label, long value) {
        super(label, value);
    }

    @Override
    public ValueJob copy() {
        return new ValueJob(label, value);
    }

}
