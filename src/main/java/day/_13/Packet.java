package day._13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Packet implements Comparable<Packet> {

    private List<Packet> packets;
    private Integer integer;

    public static Packet parseNumber(String number) {
        Deque<Packet> packets = new ArrayDeque<>();
        Packet root = new Packet(new ArrayList<>());
        packets.push(root);
        Packet currentPacket;
        for (int i = 0; i < number.length(); i++) {
            currentPacket = packets.peek();
            char c = number.charAt(i);
            if (i != 0 && c == '[') {
                Packet nestedPacket = new Packet(new ArrayList<>());
                currentPacket.add(nestedPacket);
                packets.push(nestedPacket);
            } else if (Character.isDigit(c)) {
                currentPacket.add(new Packet(Character.getNumericValue(c)));
            } else if (c == ']') {
                packets.pop();
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
            Integer leftInteger = getInteger();
            Integer rightInteger = other.getInteger();
            return leftInteger.compareTo(rightInteger);
        } else if (!isSingle() && !other.isSingle()) {
            int i = 0;
            while (i < getPackets().size() && i < other.getPackets().size()) {
                int comparison = getPackets().get(i).compareTo(other.getPackets().get(i));
                if (comparison < 0) {
                    return -1;
                } else if (comparison > 0) {
                    return 1;
                } else {
                    i++;
                }
            }
            if (i == getPackets().size() && i < other.getPackets().size()) {
                return -1;
            } else if (i == other.getPackets().size() && i < getPackets().size()) {
                return 1;
            } else {
                return 0;
            }
        } else if (isSingle() && !other.isSingle()) {
            Packet packet = new Packet(new ArrayList<>());
            packet.add(new Packet(getInteger()));
            return packet.compareTo(other);
        } else {
            Packet packet = new Packet(new ArrayList<>());
            packet.add(new Packet(other.getInteger()));
            return compareTo(packet);
        }
    }

    @Override
    public String toString() {
        return "P{" + "i: " + integer + ", ps: " + packets + '}';
    }

}
