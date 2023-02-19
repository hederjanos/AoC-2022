package day._11.operation;

import java.util.Arrays;
import java.util.List;

public class TestOperation extends Operation {

    private final int[] booleanTable = new int[2];

    public TestOperation(List<String> operation) {
        int bound = operation.size();
        for (int i = 0; i < bound; i++) {
            String[] command = operation.get(i).trim().split(" ");
            if (i == 0) {
                operator = Operator.DIVIDE;
                operand = Integer.parseInt(command[command.length - 1]);
            } else {
                booleanTable[i - 1] = Integer.parseInt(command[command.length - 1]);
            }
        }
    }

    public int[] getBooleanTable() {
        return Arrays.copyOf(booleanTable, 2);
    }

}

