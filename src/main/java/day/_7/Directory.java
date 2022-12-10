package day._7;

import java.util.*;

public class Directory extends DataUnit {

    private final List<DataUnit> dataUnits = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public List<DataUnit> getDataUnits() {
        return dataUnits;
    }

    public void addDataUnit(DataUnit dataUnit) {
        this.dataUnits.add(dataUnit);
    }

    public long getSize() {
        return getSize(0L, this);
    }

    public long getSize(Long size, Directory directory) {
        for (DataUnit dataUnit : directory.getDataUnits()) {
            if (dataUnit instanceof File) {
                size += ((File) dataUnit).getSize();
            } else {
                size += getSize(0L, (Directory) dataUnit);
            }
        }
        return size;
    }

    public long getTotalSizeOfDirectoriesWithSizeAtMost(long maxSize) {
        Set<Directory> directories = new HashSet<>();
        getTotalSizeOfDirectoriesWithSizeAtMost(maxSize, this, directories);
        return directories.stream().map(Directory::getSize).reduce(0L, Long::sum);
    }

    private void getTotalSizeOfDirectoriesWithSizeAtMost(Long maxSize, Directory directory, Set<Directory> dirs) {
        for (DataUnit dataUnit : directory.getDataUnits()) {
            if (dataUnit instanceof Directory) {
                long currentSize = ((Directory) dataUnit).getSize();
                if (currentSize <= maxSize) {
                    dirs.add((Directory) dataUnit);
                }
                getTotalSizeOfDirectoriesWithSizeAtMost(maxSize, (Directory) dataUnit, dirs);
            }
        }
    }

    public long getSmallestSizedDirectoryAtLeast(long atLeast) {
        Set<Directory> directories = new HashSet<>();
        getTotalSizeOfDirectoriesWithSizeAtMost(Long.MAX_VALUE, this, directories);
        return directories.stream()
                .map(Directory::getSize)
                .sorted()
                .filter(size -> size >= atLeast)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(name).append(" (dir)\n");
        return toString(sb, this, 1);
    }

    private String toString(StringBuilder sb, Directory directory, int level) {
        for (DataUnit dataUnit : directory.getDataUnits()) {
            sb.append(getTab(level)).append("- ").append(dataUnit.getName());
            if (dataUnit instanceof Directory) {
                sb.append(" (dir, size=").append(getSize(0L, (Directory) dataUnit)).append(")\n");
                toString(sb, (Directory) dataUnit, level + 1);
            } else {
                sb.append(" (file, size=").append(((File) dataUnit).getSize()).append(")\n");
            }
        }
        return sb.toString();
    }

    private String getTab(int level) {
        return "\t".repeat(Math.max(0, level));
    }

}
