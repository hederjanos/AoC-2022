package day._17;

public class ComplexChamberState {

    private final HeightAndRocks reference;
    private final HeightAndRocks heightAndRocks;

    public ComplexChamberState(HeightAndRocks reference, HeightAndRocks heightAndRocks) {
        this.reference = reference;
        this.heightAndRocks = heightAndRocks;
    }

    public HeightAndRocks getReference() {
        return reference;
    }

    public HeightAndRocks getHeightAndRocks() {
        return heightAndRocks;
    }

}
