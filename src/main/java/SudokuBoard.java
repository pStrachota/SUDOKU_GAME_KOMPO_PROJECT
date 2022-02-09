public class SudokuBoard {

    private final int sudokuSize = 9;
    private final int[][] board = new int[sudokuSize][sudokuSize];

    public void setValue(int row, int column, int value) {
        board[row][column] = value;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int row = 0; row < sudokuSize; row++) {
            if (row != 0 && row % 3 == 0) {
                res.append("------+-------+------\n");
            }
            for (int col = 0; col < sudokuSize; col++) {
                if (col != 0 && col % 3 == 0) {
                    res.append("| ");
                }
                res.append(this.getValue(row, col));
                res.append(" ");
            }
            res.append('\n');
        }
        return res.toString();
    }

    public int getValue(int row, int column) {
        return board[row][column];
    }

    private boolean overallCheck(int row, int column, int value) {
        return rowCheck(column, value) && columnCheck(row, value)
                && boxCheck(row, column, value);
    }

    private boolean rowCheck(int column, int value) {
        for (int row = 0; row < sudokuSize; row++) {
            if (this.getValue(row, column) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean columnCheck(int row, int value) {
        for (int column = 0; column < sudokuSize; column++) {
            if (this.getValue(row, column) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean boxCheck(int row, int column, int value) {
        int squaresInBox = (int) Math.sqrt(sudokuSize);
        int rowToCheck = (row / squaresInBox) * squaresInBox;
        int columnToCheck = (column / squaresInBox) * squaresInBox;
        for (int r = rowToCheck; r < rowToCheck + squaresInBox; r++) {
            for (int c = columnToCheck; c < columnToCheck + squaresInBox; c++) {
                if (this.getValue(r, c) == value) {
                    return false;
                }
            }
        }
        return true;
    }

}
