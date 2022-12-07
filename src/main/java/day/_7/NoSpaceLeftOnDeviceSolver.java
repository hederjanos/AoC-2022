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
            } else {
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
        }
        return directory;
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
        return root.getTotalSizeOfDirectoriesWithSizeAtMost(100000L);
    }

    @Override
    protected Long solvePartTwo() {
        long freeSpace = 70000000L - root.getSize();
        long neededSpace = 30000000L - freeSpace;
        return root.getSmallestSizedDirectoryAtLeast(neededSpace);
    }

}
