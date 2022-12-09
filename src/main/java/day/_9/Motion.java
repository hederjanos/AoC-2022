package day._9;

import util.coordinate.Direction;

public class Motion {

    private final Direction direction;
    private final int numberOfSteps;

    public Motion(String motion) {
        String[] command = motion.split(" ");
        switch ((command[0])) {
            case "U":
                direction = Direction.UP;
                break;
            case "D":
                direction = Direction.DOWN;
                break;
            case "R":
                direction = Direction.RIGHT;
                break;
            case "L":
                direction = Direction.LEFT;
                break;
            default:
                throw new IllegalArgumentException();
        }
        this.numberOfSteps = Integer.parseInt(command[1]);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

}
