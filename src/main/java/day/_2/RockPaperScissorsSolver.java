package day._2;

import util.common.Solver;

import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsSolver extends Solver<Integer> {

    private static final Map<Character, Shape> encodingMap = new HashMap<>();
    private static final Map<Character, GameState> realEncodingMap = new HashMap<>();

    static {
        encodingMap.putIfAbsent('A', Shape.ROCK);
        encodingMap.putIfAbsent('B', Shape.PAPER);
        encodingMap.putIfAbsent('C', Shape.SCISSORS);
        encodingMap.putIfAbsent('X', Shape.ROCK);
        encodingMap.putIfAbsent('Y', Shape.PAPER);
        encodingMap.putIfAbsent('Z', Shape.SCISSORS);
    }

    static {
        realEncodingMap.putIfAbsent('X', GameState.LOSE);
        realEncodingMap.putIfAbsent('Y', GameState.DRAW);
        realEncodingMap.putIfAbsent('Z', GameState.WIN);
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
        return puzzle.stream()
                .map(this::getScoreOfTurnTwo)
                .reduce(0, Integer::sum);
    }

    private int getScoreOfTurnTwo(String line) {
        int score = 0;
        Shape opponent = encodingMap.get(line.charAt(0));
        GameState gameState = realEncodingMap.get(line.charAt(2));
        if (GameState.DRAW.equals(gameState)) {
            score += opponent.getValue();
        } else if (GameState.LOSE.equals(gameState)) {
            if (Shape.ROCK.equals(opponent)) {
                score += Shape.SCISSORS.getValue();
            } else if (Shape.PAPER.equals(opponent)) {
                score += Shape.ROCK.getValue();
            } else {
                score += Shape.PAPER.getValue();
            }
        } else {
            if (Shape.ROCK.equals(opponent)) {
                score += Shape.PAPER.getValue();
            } else if (Shape.PAPER.equals(opponent)) {
                score += Shape.SCISSORS.getValue();
            } else {
                score += Shape.ROCK.getValue();
            }
        }
        score += gameState.getValue();
        return score;
    }

}
