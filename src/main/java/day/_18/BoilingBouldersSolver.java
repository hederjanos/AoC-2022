package day._18;

import util.common.Solver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BoilingBouldersSolver extends Solver<Integer> {

    private int maxX;
    private int maxY;
    private int maxZ;
    private final Set<Coordinate3D> lavaCubes;
    private int fullSurfaceArea;

    public BoilingBouldersSolver(String filename) {
        super(filename);
        lavaCubes = puzzle.stream().map(coordinate -> {
            Coordinate3D coordinate3D = new Coordinate3D(coordinate);
            maxX = Math.max(maxX, coordinate3D.getX());
            maxY = Math.max(maxY, coordinate3D.getY());
            maxZ = Math.max(maxZ, coordinate3D.getZ());
            return coordinate3D;
        }).collect(Collectors.toSet());
    }

    @Override
    protected Integer solvePartOne() {
        fullSurfaceArea = getFullSurfaceArea();
        return fullSurfaceArea;
    }

    private int getFullSurfaceArea() {
        return lavaCubes.stream()
                .map(coordinate3D -> getNumberOfFreeSides(coordinate3D, lavaCubes))
                .reduce(0, Integer::sum);
    }

    private int getNumberOfFreeSides(Coordinate3D coordinate, Set<Coordinate3D> cubes) {
        int numberOfSides = ReducedDirection3D.values().length;
        for (Coordinate3D neighbour : coordinate.getSideCoordinates()) {
            if (cubes.contains(neighbour)) {
                numberOfSides--;
            }
        }
        return numberOfSides;
    }

    @Override
    protected Integer solvePartTwo() {
        Set<Coordinate3D> outsideAirCubes = getOutsideAirCubes();
        Set<Coordinate3D> insideAirCubes = getInsideAirCubes(outsideAirCubes);
        Integer interiorSurfaceArea = insideAirCubes.stream()
                .map(coordinate3D -> getNumberOfFreeSides(coordinate3D, insideAirCubes))
                .reduce(0, Integer::sum);
        return fullSurfaceArea - interiorSurfaceArea;
    }

    private Set<Coordinate3D> getOutsideAirCubes() {
        Set<Coordinate3D> outsideAirCubes = new HashSet<>();
        Deque<Coordinate3D> deque = new ArrayDeque<>();
        deque.offer(lavaCubes.stream().findFirst().orElseThrow());
        while (!deque.isEmpty()) {
            Coordinate3D current = deque.pop();
            for (Coordinate3D neighbour : current.getSideCoordinates()) {
                if (isInside(neighbour) && !outsideAirCubes.contains(neighbour) && !lavaCubes.contains(neighbour)) {
                    deque.offer(neighbour);
                    outsideAirCubes.add(neighbour);
                }
            }
        }
        return outsideAirCubes;
    }

    private boolean isInside(Coordinate3D coordinate3D) {
        return coordinate3D.getX() <= maxX && coordinate3D.getX() >= 0 &&
               coordinate3D.getY() <= maxY && coordinate3D.getY() >= 0 &&
               coordinate3D.getZ() <= maxZ && coordinate3D.getZ() >= 0;
    }

    private Set<Coordinate3D> getInsideAirCubes(Set<Coordinate3D> outsideAirCubes) {
        Set<Coordinate3D> insideAirCubes = new HashSet<>();
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                for (int k = 0; k <= maxZ; k++) {
                    Coordinate3D coordinate3D = new Coordinate3D(i, j, k);
                    if (!outsideAirCubes.contains(coordinate3D) && !lavaCubes.contains(coordinate3D)) {
                        insideAirCubes.add(coordinate3D);
                    }
                }
            }
        }
        return insideAirCubes;
    }

}
