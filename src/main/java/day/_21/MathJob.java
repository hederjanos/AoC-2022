package day._21;

public class MathJob extends AbstractJob {

    private String operation;

    public MathJob(String label, String operation) {
        super(label, 0L);
        this.operation = operation;
    }

    public MathJob(String label, String operation, long value) {
        super(label, 0L);
        this.operation = operation;
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public MathJob copy() {
        return new MathJob(label, operation, value);
    }

}
