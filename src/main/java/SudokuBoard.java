public class SudokuBoard {

    private final int sudokuSize = 9;
    private final SudokuField[][] board = new SudokuField[sudokuSize][sudokuSize];
    BacktrackingSudokuSolver backtrackingSudokuSolver;

    public SudokuBoard(BacktrackingSudokuSolver backtrackingSudokuSolver) {
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                this.board[row][column] = new SudokuField();
            }
        }
        this.backtrackingSudokuSolver = backtrackingSudokuSolver;
    }

    public void solveGame() {
        this.backtrackingSudokuSolver.solve(this);
    }

    SudokuRow getRow(int y) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int column = 0; column < sudokuSize; column++) {
            sudokuRow.setSudokuField(column, this.board[y][column].getFieldValue());
        }
        return sudokuRow;
    }

    private boolean isRowCorrect() {
        for (int row = 0; row < sudokuSize; row++) {
            if (!this.getRow(row).verify()) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnCorrect() {
        for (int column = 0; column < sudokuSize; column++) {
            if (!this.getColumn(column).verify()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkBoard() {
        return isRowCorrect() && isColumnCorrect() && isBoxCorrect();
    }

    SudokuColumn getColumn(int x) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int row = 0; row < sudokuSize; row++) {
            sudokuColumn.setSudokuField(row, this.board[row][x].getFieldValue());
        }
        return sudokuColumn;
    }

    SudokuBox getBox(int x, int y) {
        int boxLenght = (int) Math.sqrt(sudokuSize);
        SudokuBox sudokuBox = new SudokuBox();
        int rowToWrite = (x / boxLenght) * boxLenght;
        int columnToWrite = (y / boxLenght) * boxLenght;
        int index = 0;
        for (int r = rowToWrite; r < rowToWrite + boxLenght; r++) {
            for (int c = columnToWrite; c < columnToWrite + boxLenght; c++) {
                sudokuBox.setSudokuField(index, this.getValue(r, c));
                index++;
            }
        }
        return sudokuBox;
    }

    private boolean isBoxCorrect() {
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                if (!this.getBox(row, column).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getValue(int row, int column) {
        return board[row][column].getFieldValue();
    }

    public void setValue(int row, int column, int value) {
        board[row][column].setFieldValue(value);
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
}


