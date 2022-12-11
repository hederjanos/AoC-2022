package day._11.operation;

import java.util.List;
import java.util.stream.IntStream;

public class TestOperation extends Operation {

    private final int[] booleanTable = new int[2];

    public TestOperation(List<String> operation) {
        IntStream.range(0, operation.size())
                .forEach(i -> {
                    String[] command = operation.get(i).trim().split(" ");
                    if (i == 0) {
                        operator = Operator.DIVIDE;
                        operand = Integer.parseInt(command[command.length - 1]);
                    } else {
                        booleanTable[i - 1] = Integer.parseInt(command[command.length - 1]);
                    }
                });
    }

    public int[] getBooleanTable() {
        return booleanTable;
    }
}

