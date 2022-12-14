package day._13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PacketTest {

    @Test
    void test1() {
        String number1 = "[1,1,3,1,1]";
        String number2 = "[1,1,5,1,1]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(-1, n1.compareTo(n2));
    }

    @Test
    void test2() {
        String number1 = "[[1],[2,3,4]]";
        String number2 = "[[1],4]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(-1, n1.compareTo(n2));
    }

    @Test
    void test3() {
        String number1 = "[9]";
        String number2 = "[[8,7,6]]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(1, n1.compareTo(n2));
    }

    @Test
    void test4() {
        String number1 = "[[4,4],4,4]";
        String number2 = "[[4,4],4,4,4]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(-1, n1.compareTo(n2));
    }

    @Test
    void test5() {
        String number1 = "[7,7,7,7]";
        String number2 = "[7,7,7]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(1, n1.compareTo(n2));
    }

    @Test
    void test6() {
        String number1 = "[]";
        String number2 = "[3]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(-1, n1.compareTo(n2));
    }

    @Test
    void test7() {
        String number1 = "[[[]]]";
        String number2 = "[[]]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(1, n1.compareTo(n2));
    }

    @Test
    void test8() {
        String number1 = "[1,[2,[3,[4,[5,6,7]]]],8,9]";
        String number2 = "[1,[2,[3,[4,[5,6,0]]]],8,9]";
        Packet n1 = Packet.parseAPacket(number1);
        Packet n2 = Packet.parseAPacket(number2);
        Assertions.assertEquals(1, n1.compareTo(n2));
    }

}
