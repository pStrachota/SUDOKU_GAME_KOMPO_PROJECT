/*
 * #%L
 * KOMPO_PROJECT
 * %%
 * Copyright (C) 2021 - 2022 TUL
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SudokuBoard implements Serializable, Cloneable {
    private final int sudokuSize = 9;
    private final List<SudokuField> board = Arrays.asList(new SudokuField[sudokuSize * sudokuSize]);
    private final List<Boolean> booleanBoardToCheck =
            Arrays.asList(new Boolean[sudokuSize * sudokuSize]);
    BacktrackingSudokuSolver backtrackingSudokuSolver;

    public SudokuBoard(BacktrackingSudokuSolver backtrackingSudokuSolver) {
        for (int index = 0; index < sudokuSize * sudokuSize; index++) {
            this.board.set(index, new SudokuField());
        }
        for (int index = 0; index < sudokuSize * sudokuSize; index++) {
            this.booleanBoardToCheck.set(index, Boolean.FALSE);
        }
        this.backtrackingSudokuSolver = backtrackingSudokuSolver;
    }

    public void setBooleanValue(int row, int column, boolean value) {
        this.booleanBoardToCheck.set(row * sudokuSize + column, value);
    }

    public Boolean getBooleanValue(int row, int column) {
        return this.booleanBoardToCheck.get(row * sudokuSize + column);
    }

    public void solveGame() {
        this.backtrackingSudokuSolver.solve(this);
    }

    public boolean checkBoard() {
        return isRowCorrect() && isColumnCorrect() && isBoxCorrect();
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

    public SudokuRow getRow(int y) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int column = 0; column < sudokuSize; column++) {
            sudokuRow.setSudokuField(column,
                    this.board.get(y * sudokuSize + column).getFieldValue());
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int row = 0; row < sudokuSize; row++) {
            sudokuColumn.setSudokuField(row, this.board.get(x * sudokuSize + row).getFieldValue());
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int x, int y) {
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

    public int getValue(int row, int column) {
        return this.board.get(row * sudokuSize + column).getFieldValue();
    }

    public boolean isValueCorrect(int row, int column) {
            for (int index = 0; index < sudokuSize; index++) {
                if (this.getValue(row, column) == this.getValue(row, index)
                        && index != column) {
                    return false;
                }
            }
        for (int index = 0; index < sudokuSize; index++) {
            if (this.getValue(row, column) == this.getValue(index, column)
                    && index != row) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board)
                .isEquals();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SudokuBoard sudokuBoardClone = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                sudokuBoardClone.setValue(row, column, this.getValue(row, column));
            }
        }
        return sudokuBoardClone;
    }

    public void setValue(int row, int column, int value) {
        this.board.get(row * sudokuSize + column).setFieldValue(value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
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


