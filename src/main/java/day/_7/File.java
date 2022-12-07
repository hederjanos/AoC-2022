package day._7;

public class File extends DataUnit {

    private long size;

    public File(long size, String name) {
        super(name);
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
