package day._7;

public abstract class DataUnit {

    protected Directory parent;
    protected String name;

    protected DataUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void setParent(Directory parent) {
        this.parent = parent;
    }

}
