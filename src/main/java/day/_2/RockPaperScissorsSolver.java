package day._2;

import util.common.Solver;

import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsSolver extends Solver<Integer> {

    private static final Map<Character, Shape> encodingMap = new HashMap<>();

    static {
        encodingMap.putIfAbsent('A', Shape.ROCK);
        encodingMap.putIfAbsent('B', Shape.PAPER);
        encodingMap.putIfAbsent('C', Shape.SCISSORS);
        encodingMap.putIfAbsent('X', Shape.ROCK);
        encodingMap.putIfAbsent('Y', Shape.PAPER);
        encodingMap.putIfAbsent('Z', Shape.SCISSORS);
    }

    public RockPaperScissorsSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::getScoreOfTurn)
                .reduce(0, Integer::sum);
    }

    private int getScoreOfTurn(String line) {
        int score = 0;
        Shape opponent = encodingMap.get(line.charAt(0));
        Shape own = encodingMap.get(line.charAt(2));
        if (opponent.equals(own)) {
            score += GameState.DRAW.getValue();
        } else if (Shape.ROCK.equals(opponent)) {
            if (Shape.PAPER.equals(own)) {
                score += GameState.WIN.getValue();
            } else {
                score += GameState.LOSE.getValue();
            }
        } else if (Shape.PAPER.equals(opponent)) {
            if (Shape.ROCK.equals(own)) {
                score += GameState.LOSE.getValue();
            } else {
                score += GameState.WIN.getValue();
            }
        } else {
            if (Shape.ROCK.equals(own)) {
                score += GameState.WIN.getValue();
            } else {
                score += GameState.LOSE.getValue();
            }
        }
        score += own.getValue();
        return score;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
