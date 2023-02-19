package day._20;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;

public class GrovePositioningSystemSolver extends Solver<Long> {

    private List<Node> nodes;
    private static final int DECRYPTION_KEY = 811589153;

    public GrovePositioningSystemSolver(String filename) {
        super(filename);
    }

    @Override
    protected Long solvePartOne() {
        return solve(1, 1);
    }

    private long solve(int multiplier, int rounds) {
        nodes = parseInput(multiplier);
        for (int i = 0; i < rounds; i++) {
            mixInPlace();
        }
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
        for (Node node : nodes) {
            int modSize = size - 1;
            int modulo = (int) (node.getValue() % modSize + modSize) % modSize;
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
            int j = 0;
            Node insert = node.getPrevious();
            while (j < modulo) {
                insert = insert.getNext();
                j++;
            }
            node.setPrevious(insert);
            node.setNext(insert.getNext());
            node.getPrevious().setNext(node);
            node.getNext().setPrevious(node);
        }
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
        return solve(DECRYPTION_KEY, 10);
    }

}
