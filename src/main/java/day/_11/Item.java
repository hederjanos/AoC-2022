package day._11;

import day._11.operation.MathOperation;
import day._11.operation.Operator;
import day._11.operation.TestOperation;

public class Item {

    private long worryLevel;

    public Item(String worryLevel) {
        this.worryLevel = Integer.parseInt(worryLevel);
    }

    public Item(long worryLevel) {
        this.worryLevel = worryLevel;
    }

    public void doMath(MathOperation operation) {
        Operator operator = operation.getOperator();
        switch (operator) {
            case ADD:
                worryLevel += operation.getOperand();
                break;
            case MULTIPLY:
                int operand = operation.getOperand();
                worryLevel *= operand >= 0 ? operation.getOperand() : worryLevel;
                break;
            case DIVIDE:
                worryLevel = Math.floorDiv(worryLevel, operation.getOperand());
                break;
            case MOD:
                worryLevel %= operation.getOperand();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int doTest(TestOperation operation) {
        return operation.getBooleanTable()[(worryLevel % operation.getOperand() == 0) ? 0 : 1];
    }

    public long getWorryLevel() {
        return worryLevel;
    }

    @Override
    public String toString() {
        return worryLevel + "";
    }

}
