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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    SudokuBoard sudokuBoard;

    @BeforeEach
    void init() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    }


    @Test
    void checkForCorrectArrangment() {
        System.out.println("Check for correct arrangment");
        sudokuBoard.solveGame();
        System.out.println(sudokuBoard);
        assertTrue(sudokuBoard.checkBoard());
    }

    @Test
    void checkForIncorrectArrangment() {
        System.out.println("Check for incorrect arrangment");
        System.out.println(sudokuBoard);
        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    void checkForDuplicates() {
        System.out.println("Check if two call fillboard method generated diffrent results");
        sudokuBoard.solveGame();
        System.out.println(sudokuBoard);
        String baseSudokuGrid = sudokuBoard.toString();
        sudokuBoard.solveGame();
        System.out.println(sudokuBoard);
        String nextSudokuGrid = sudokuBoard.toString();
        assertFalse(baseSudokuGrid.equals(nextSudokuGrid));
    }

    @Test
    void setFieldValueTest() {
        System.out.println("Set sudoku value");
        sudokuBoard.setValue(0, 0, 1);
        assertEquals(1, sudokuBoard.getValue(0, 0));
    }

    @Test
    void setIncorrectFieldValueTest() {
        System.out.println("When incorrect sudoku value throw Illegal argument exception");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sudokuBoard.setValue(0, 0, 10));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Incorrect sudoku value";
        assertEquals(actualMessage, expectedMessage);
    }

}