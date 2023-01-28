package day._7;

import util.common.Solver;

import java.util.ArrayDeque;
import java.util.Deque;

public class NoSpaceLeftOnDeviceSolver extends Solver<Long> {

    private final Directory root;

    public NoSpaceLeftOnDeviceSolver(String filename) {
        super(filename);
        root = parseInput();
    }

    private Directory parseInput() {
        Deque<DataUnit> files = new ArrayDeque<>();
        Directory directory = null;
        for (String line : puzzle) {
            if (line.startsWith("$")) {
                directory = parseDirectory(files, directory, line);
            } else {
                parseDataUnits(files, line);
            }
        }
        return directory;
    }

    private Directory parseDirectory(Deque<DataUnit> files, Directory directory, String line) {
        String[] command = line.substring(2).split(" ");
        if (command[0].equals("cd")) {
            if (command[1].equals("/")) {
                directory = new Directory("/");
                files.push(directory);
            } else if (command[1].equals("..")) {
                files.pop();
            } else {
                Directory currentDir = (Directory) files.peek();
                files.push(findDirectory(currentDir, command[1]));
            }
        }
        return directory;
    }

    private void parseDataUnits(Deque<DataUnit> files, String line) {
        String[] file = line.split(" ");
        Directory currentDir = (Directory) files.peek();
        if (line.startsWith("dir")) {
            Directory newDir = new Directory(file[1]);
            newDir.setParent(currentDir);
            currentDir.addDataUnit(newDir);
        } else {
            File newFile = new File(Long.parseLong(file[0]), file[1]);
            newFile.setParent(currentDir);
            currentDir.addDataUnit(newFile);
        }
    }

    private Directory findDirectory(Directory current, String name) {
        Directory directory = null;
        for (DataUnit dataUnit : current.getDataUnits()) {
            if (dataUnit instanceof Directory && dataUnit.getName().equals(name)) {
                directory = (Directory) dataUnit;
            }
        }
        return directory;
    }

    @Override
    protected Long solvePartOne() {
        return root.getTotalSizeOfDirectoriesWithSizeAtMost(100_000L);
    }

    @Override
    protected Long solvePartTwo() {
        long freeSpace = 70_000_000L - root.getSize();
        long neededSpace = 30_000_000L - freeSpace;
        return root.getSmallestSizedDirectoryAtLeast(neededSpace);
    }

}
