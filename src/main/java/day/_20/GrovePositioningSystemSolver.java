package day._20;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GrovePositioningSystemSolver extends Solver<Integer> {

    private final List<Node> nodes;

    public GrovePositioningSystemSolver(String filename) {
        super(filename);
        nodes = parseInput();
    }

    private List<Node> parseInput() {
        List<Node> nodeList = new ArrayList<>();
        Node prev = new Node(Integer.parseInt(puzzle.get(0)));
        nodeList.add(prev);
        Node head = prev;
        for (int i = 1; i < puzzle.size(); i++) {
            head = new Node(Integer.parseInt(puzzle.get(i)));
            nodeList.add(head);
            head.setPrevious(prev);
            prev.setNext(head);
            prev = head;
        }
        head.setNext(nodeList.get(0));
        nodeList.get(0).setPrevious(head);
        return nodeList;
    }

    @Override
    protected Integer solvePartOne() {
        mixInPlace();
        return getSumOfBorders();
    }

    private void mixInPlace() {
        int size = nodes.size();
        IntStream.range(0, size)
                .forEachOrdered(i -> {
                    int modSize = size - 1;
                    Node current = nodes.get(i);
                    int modulo = (current.getValue() % modSize + modSize) % modSize;
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

    private int getSumOfBorders() {
        Node current = nodes.stream().filter(node -> node.getValue() == 0).findFirst().orElseThrow();
        int sum = 0;
        for (int i = 0; i <= 3000; i++) {
            if (i % 1000 == 0) {
                sum += current.getValue();
            }
            current = current.getNext();
        }
        return sum;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
