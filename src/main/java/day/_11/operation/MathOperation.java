package day._11.operation;

public class MathOperation extends Operation {

    public MathOperation(String operation) {
        String[] command = operation.trim().split(" ");
        if (operation.contains("*")) {
            operator = Operator.MULTIPLY;
            if (command[command.length - 1].equals("old")) {
                operand = -1;
            } else {
                operand = Integer.parseInt(command[command.length - 1]);
            }
        } else {
            operator = Operator.ADD;
            operand = Integer.parseInt(command[command.length - 1]);
        }
    }

    public MathOperation(Operator operator, int operand) {
        this.operator = operator;
        this.operand = operand;
    }

}
