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

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private final int sudokuSize = 9;

    @Override
    public void solve(SudokuBoard sudokuBoard) {
        this.makeRandom(sudokuBoard);
        this.solveSudoku(sudokuBoard);
    }

    private boolean solveSudoku(SudokuBoard sudokuBoard) {
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                if (sudokuBoard.getValue(row, column) == 0) {
                    for (int value = 1; value <= sudokuSize; value++) {
                        if (this.overallCheck(row, column, value, sudokuBoard)) {
                            sudokuBoard.setValue(row, column, value);
                            if (this.solveSudoku(sudokuBoard)) {
                                return true;
                            } else {
                                sudokuBoard.setValue(row, column, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


    private boolean overallCheck(int row, int column, int value, SudokuBoard sudokuBoard) {
        return rowCheck(column, value, sudokuBoard) && columnCheck(row, value, sudokuBoard)
                && boxCheck(row, column, value, sudokuBoard);
    }

    private boolean rowCheck(int column, int value, SudokuBoard sudokuBoard) {
        for (int row = 0; row < sudokuSize; row++) {
            if (sudokuBoard.getValue(row, column) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean columnCheck(int row, int value, SudokuBoard sudokuBoard) {
        for (int column = 0; column < sudokuSize; column++) {
            if (sudokuBoard.getValue(row, column) == value) {
                return false;
            }
        }
        return true;
    }

    private void clearBoard(SudokuBoard sudokuBoard) {
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                sudokuBoard.setValue(row, column, 0);
            }
        }
    }

    private boolean boxCheck(int row, int column, int value, SudokuBoard sudokuBoard) {
        int boxLenght = (int) Math.sqrt(sudokuSize); //3
        int rowToCheck = (row / boxLenght) * boxLenght;
        int columnToCheck = (column / boxLenght) * boxLenght;
        for (int r = rowToCheck; r < rowToCheck + boxLenght; r++) {
            for (int c = columnToCheck; c < columnToCheck + boxLenght; c++) {
                if (sudokuBoard.getValue(r, c) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private void makeRandom(SudokuBoard sudokuBoard) {
        this.clearBoard(sudokuBoard);
        Random random = new Random();
        for (int i = 0; i < sudokuSize; i++) {
            int randomRow = random.nextInt(9);
            int randomColumn = random.nextInt(9);
            int randomValue = random.nextInt(9) + 1;
            if (overallCheck(randomRow, randomColumn, randomValue, sudokuBoard)) {
                sudokuBoard.setValue(randomRow, randomColumn, randomValue);
            }
        }
    }
}
