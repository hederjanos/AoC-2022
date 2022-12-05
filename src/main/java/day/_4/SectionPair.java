package day._4;

import java.util.List;

public class SectionPair {

    private final Section first;
    private final Section last;

    public SectionPair(List<Section> sections) {
        first = sections.get(0);
        last = sections.get(1);
    }

    public boolean isContaining() {
        return first.fullyContains(last) || last.fullyContains(first);
    }

    public boolean isOverlapping() {
        return first.overlapsWith(last) || last.overlapsWith(first);
    }

}
