package day._4;

public class Section {

    private final int start;
    private final int end;

    public Section(String section) {
        String[] points = section.split("-");
        this.start = Integer.parseInt(points[0]);
        this.end = Integer.parseInt(points[1]);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean fullyContains(Section section) {
        return section.getStart() >= getStart() && section.getEnd() <= getEnd();
    }

    public boolean overlapsWith(Section section) {
        return getEnd() >= section.getStart() && getStart() <= section.getEnd();
    }

    @Override
    public String toString() {
        return "(" + start + "," + end + ")";
    }

}
