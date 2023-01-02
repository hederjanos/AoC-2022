package day._20;

public class Node {

    private final long value;
    private Node previous;
    private Node next;

    public Node(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public Node getPrevious() {
        return previous;
    }

    void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }

}
