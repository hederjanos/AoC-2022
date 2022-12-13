package day._13;

public class Application {

    public static void main(String[] args) {
        String number1 = "[1,[2,[3,[4,[5,6,7]]]],8,9]";
        String number2 = "[1,[2,[3,[4,[5,6,0]]]],8,9]";
        Packet n1 = Packet.parseNumber(number1);
        Packet n2 = Packet.parseNumber(number2);
        System.out.println(n1.compareTo(n2));
        System.out.println();
    }

}
