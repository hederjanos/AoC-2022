package day._5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class WareHouse {

    private final List<Deque<Character>> stacks = new ArrayList<>();

    public WareHouse(String numberOfStacks, List<String> crateLines) {
        int stackSize = Integer.parseInt(numberOfStacks);
        IntStream.range(0, stackSize).forEach(i -> stacks.add(new ArrayDeque<>()));
        IntStream.range(0, crateLines.size())
                .forEach(i -> {
                    char[] cratesPerRow = parseCratesInLine(crateLines.get(i));
                    IntStream.range(0, stackSize)
                            .forEach(j -> {
                                int index = j * 3 + (j == 0 ? 1 : j + 1);
                                if (cratesPerRow[index] != '\u0000') {
                                    stacks.get(j).addFirst(cratesPerRow[index]);
                                }
                            });
                });
    }

    private char[] parseCratesInLine(String crates) {
        char[] characters = new char[stacks.size() * 4 - 1];
        for (int i = 0; i < crates.length(); i++) {
            char c = crates.charAt(i);
            if (Character.isAlphabetic(c)) {
                characters[i] = c;
            }
        }
        return characters;
    }

    public void processProcedures(List<Procedure> procedures) {
        procedures.forEach(procedure -> IntStream.range(0, procedure.getNumberOfMoves())
                .forEach(i -> stacks.get(procedure.getTo()).addLast(stacks.get(procedure.getFrom()).removeLast())));
    }

    public void processProceduresEnhanced(List<Procedure> procedures) {
        procedures.forEach(procedure -> {
            Deque<Character> helperDeque = new ArrayDeque<>();
            int counter = 0;
            while (counter < procedure.getNumberOfMoves()) {
                helperDeque.push(stacks.get(procedure.getFrom()).removeLast());
                counter++;
            }
            while (!helperDeque.isEmpty()) {
                stacks.get(procedure.getTo()).addLast(helperDeque.pop());
            }
        });
    }

    public String getTopCrates() {
        return stacks.stream()
                .map(Deque::getLast)
                .collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
    }

}
