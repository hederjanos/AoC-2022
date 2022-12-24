package day._20;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GrovePositioningSystemSolver extends Solver<Long> {

    private List<Node> nodes;
    private static final int DECRYPTION_KEY = 811589153;

    public GrovePositioningSystemSolver(String filename) {
        super(filename);
    }

    @Override
    protected Long solvePartOne() {
        nodes = parseInput(1);
        mixInPlace();
        return getSumOfBorders();
    }

    private List<Node> parseInput(int multiplier) {
        List<Node> nodeList = new ArrayList<>();
        Node prev = new Node(Long.parseLong(puzzle.get(0)) * multiplier);
        nodeList.add(prev);
        Node head = prev;
        for (int i = 1; i < puzzle.size(); i++) {
            head = new Node(Long.parseLong(puzzle.get(i)) * multiplier);
            nodeList.add(head);
            head.setPrevious(prev);
            prev.setNext(head);
            prev = head;
        }
        head.setNext(nodeList.get(0));
        nodeList.get(0).setPrevious(head);
        return nodeList;
    }

    private void mixInPlace() {
        int size = nodes.size();
        IntStream.range(0, size)
                .forEachOrdered(i -> {
                    int modSize = size - 1;
                    Node current = nodes.get(i);
                    int modulo = (int) (current.getValue() % modSize + modSize) % modSize;
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    int j = 0;
                    Node insert = current.getPrevious();
                    while (j < modulo) {
                        insert = insert.getNext();
                        j++;
                    }
                    current.setPrevious(insert);
                    current.setNext(insert.getNext());
                    current.getPrevious().setNext(current);
                    current.getNext().setPrevious(current);
                });
    }

    private long getSumOfBorders() {
        Node current = nodes.stream().filter(node -> node.getValue() == 0).findFirst().orElseThrow();
        long sum = 0L;
        for (int i = 0; i <= 3000; i++) {
            if (i % 1000 == 0) {
                sum += current.getValue();
            }
            current = current.getNext();
        }
        return sum;
    }

    @Override
    protected Long solvePartTwo() {
        nodes = parseInput(DECRYPTION_KEY);
        IntStream.range(0, 10).forEach(i -> mixInPlace());
        return getSumOfBorders();
    }

}
