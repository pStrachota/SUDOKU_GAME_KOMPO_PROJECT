import java.util.Random;


public class SudokuBoard {

    BacktrackingSudokuSolver backtrackingSudokuSolver;
    private final int sudokuSize = 9;
    private final int[][] board = new int[sudokuSize][sudokuSize];

    public SudokuBoard(BacktrackingSudokuSolver backtrackingSudokuSolver) {
        this.backtrackingSudokuSolver = backtrackingSudokuSolver;
    }


    public int getValue(int row, int column) {
        return board[row][column];
    }

    public boolean checkBoard() {
        return isRowCorrect() && isColumnCorrect() && isBoxCorrect();
    }

    private boolean isRowCorrect() {
        for (int column = 0; column < sudokuSize; column++) {
            for (int row = 0; row < sudokuSize; row++) {
                for (int index = 0; index < sudokuSize; index++) {
                    if (this.getValue(row, column) == this.getValue(index, column)
                            && row != index) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean isColumnCorrect() {
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                for (int number = 0; number < sudokuSize; number++) {
                    if (this.getValue(row, column) == this.getValue(row, number)
                            && column != number) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isBoxCorrect() {
        int boxLenght = 3;
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                int rowToCheck = (row / boxLenght) * boxLenght;
                int columnToCheck = (column / boxLenght) * boxLenght;
                for (int r = rowToCheck; r < rowToCheck + boxLenght; r++) {
                    for (int c = columnToCheck; c < columnToCheck + boxLenght; c++) {
                        for (int index = 0; index < boxLenght; index++) {
                            if (this.getValue(r, c) == this.getValue(r, index) && index != c) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

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

    public void solveGame() {
        this.backtrackingSudokuSolver.solve(this);
    }


}
