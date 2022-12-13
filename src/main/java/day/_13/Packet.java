package day._13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Packet implements Comparable<Packet> {

    private List<Packet> packets;
    private Integer integer;

    public static Packet parseNumber(String number) {
        Deque<Packet> numbers = new ArrayDeque<>();
        Packet root = new Packet(new ArrayList<>());
        numbers.push(root);
        Packet currentNumber;
        for (int i = 0; i < number.length(); i++) {
            currentNumber = numbers.peek();
            char c = number.charAt(i);
            if (i != 0 && c == '[') {
                Packet nestedNumber = new Packet(new ArrayList<>());
                currentNumber.add(nestedNumber);
                numbers.push(nestedNumber);
            } else if (Character.isDigit(c)) {
                currentNumber.add(new Packet(Character.getNumericValue(c)));
            } else if (c == ']') {
                numbers.pop();
            }
        }
        return root;
    }

    public Packet(List<Packet> packets) {
        this.packets = packets;
    }

    public Packet(Integer integer) {
        this.integer = integer;
    }

    public Integer getInteger() {
        return integer;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public boolean isSingle() {
        return integer != null;
    }

    public void add(Packet packet) {
        packets.add(packet);
    }

    @Override
    public int compareTo(Packet other) {
        if (this.isSingle() && other.isSingle()) {
            Integer leftInteger = this.getInteger();
            Integer rightInteger = other.getInteger();
            return leftInteger.compareTo(rightInteger);
        } else if (!this.isSingle() && !other.isSingle()) {
            int i = 0;
            while (i < this.getPackets().size() && i < other.getPackets().size()) {
                int comparison = this.getPackets().get(i).compareTo(other.getPackets().get(i));
                if (comparison < 0) {
                    return -1;
                } else if (comparison > 0) {
                    return 1;
                }
                i++;
            }
            if (i == this.getPackets().size() && i < other.getPackets().size()) {
                return -1;
            } else if (i == other.getPackets().size() && i < this.getPackets().size()) {
                return 1;
            } else {
                return 0;
            }
        } else if (this.isSingle() && !other.isSingle()) {
            Packet packet = new Packet(new ArrayList<>());
            packet.add(new Packet(this.getInteger()));
            return packet.compareTo(other);
        } else {
            Packet packet = new Packet(new ArrayList<>());
            packet.add(new Packet(other.getInteger()));
            return this.compareTo(packet);
        }
    }

    @Override
    public String toString() {
        return "P{" + "i: " + integer + ", ps: " + packets + '}';
    }

}
