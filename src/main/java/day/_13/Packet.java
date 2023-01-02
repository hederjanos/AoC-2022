package day._13;

import java.util.*;

public class Packet implements Comparable<Packet> {

    private List<Packet> packets;
    private Integer integer;

    public static Packet parseAPacket(String pair) {
        Deque<Packet> packets = new ArrayDeque<>();
        Packet root = new Packet(new ArrayList<>());
        packets.push(root);
        Packet currentPacket;
        for (int i = 0; i < pair.length(); i++) {
            currentPacket = packets.peek();
            char c = pair.charAt(i);
            if (i != 0 && c == '[') {
                Packet nestedPacket = new Packet(new ArrayList<>());
                currentPacket.add(nestedPacket);
                packets.push(nestedPacket);
            } else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                i = collectNumberChars(pair, i, sb);
                Integer integer = Integer.parseInt(sb.toString());
                currentPacket.add(new Packet(integer));
            } else if (c == ']') {
                packets.pop();
            }
        }
        return root;
    }

    private static int collectNumberChars(String number, int i, StringBuilder sb) {
        while (i < number.length() && Character.isDigit(number.charAt(i))) {
            sb.append(number.charAt(i));
            i++;
        }
        return --i;
    }

    private Packet(List<Packet> packets) {
        this.packets = packets;
    }

    private Packet(Integer integer) {
        this.integer = integer;
    }

    private Integer getInteger() {
        return integer;
    }

    private List<Packet> getPackets() {
        return packets;
    }

    private boolean isSingle() {
        return integer != null;
    }

    private void add(Packet packet) {
        packets.add(packet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Packet)) return false;
        Packet packet = (Packet) o;
        return Objects.equals(getPackets(), packet.getPackets()) && Objects.equals(getInteger(), packet.getInteger());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPackets(), getInteger());
    }

    @Override
    public int compareTo(Packet other) {
        if (isSingle() && other.isSingle()) {
            Integer leftInteger = getInteger();
            Integer rightInteger = other.getInteger();
            return leftInteger.compareTo(rightInteger);
        } else if (!isSingle() && !other.isSingle()) {
            return compareNestedPackets(other);
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

    private int compareNestedPackets(Packet other) {
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
    }

    @Override
    public String toString() {
        return "P{" + "i: " + integer + ", ps: " + packets + '}';
    }

}
