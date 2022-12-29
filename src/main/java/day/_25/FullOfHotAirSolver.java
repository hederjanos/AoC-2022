package day._25;

import util.common.Solver;

public class FullOfHotAirSolver extends Solver<String> {

    public FullOfHotAirSolver(String filename) {
        super(filename);
    }

    @Override
    protected String solvePartOne() {
        Long sum = puzzle.stream().map(this::convertSNAFUToDecimal).reduce(0L, Long::sum);
        return convertDecimalToSNAFU(sum);
    }

    @Override
    protected String solvePartTwo() {
        return null;
    }

    private long convertSNAFUToDecimal(String snafu) {
        long decimal = 0L;
        for (int i = snafu.length() - 1; i >= 0; i--) {
            long addition;
            char c = snafu.charAt(snafu.length() - i - 1);
            long pow = (long) Math.pow(5L, i);
            switch (c) {
                case '-':
                    addition = -1L * pow;
                    break;
                case '=':
                    addition = -2L * pow;
                    break;
                case '0':
                    addition = 0L;
                    break;
                case '1':
                    addition = pow;
                    break;
                case '2':
                    addition = 2L * pow;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            decimal += addition;
        }
        return decimal;
    }

    private String convertDecimalToSNAFU(Long decimal) {
        StringBuilder snafu = new StringBuilder();
        while (decimal > 0) {
            int remainder = (int) (decimal % 5);
            switch (remainder) {
                case 0:
                    snafu.append(0);
                    break;
                case 1:
                    snafu.append(1);
                    break;
                case 2:
                    snafu.append(2);
                    break;
                case 3:
                    snafu.append("=");
                    break;
                case 4:
                    snafu.append("-");
                    break;
                default:
                    throw new IllegalStateException();
            }
            if (remainder > 2L) {
                decimal += 5L;
            }
            decimal /= 5L;
        }
        return snafu.reverse().toString();
    }

}
