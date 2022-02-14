/*
 * #%L
 * KOMPO_PROJECT
 * %%
 * Copyright (C) 2021 - 2022 Piotr Strachota
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuEntityTest {

    SudokuEntity sudokuEntity;

    @BeforeEach
    void init() {
        sudokuEntity = new SudokuRow();
    }

    @Test
    void setSudokuFieldTest() {
        System.out.println("Set sudoku entity (row) test");
        sudokuEntity.setSudokuField(0, 1);
        assertEquals(1, sudokuEntity.getSudokuField(0));
    }


    @Test
    void hashcodeNegativeTest() {
        System.out.println("Check sudoku entity (row) object hashcode "
                + "with different sudoku entity object hashcode");
        SudokuEntity differentSudokuEntity = new SudokuRow();
        sudokuEntity.setSudokuField(0, 2);
        differentSudokuEntity.setSudokuField(0, 1);
        assertNotEquals(sudokuEntity.hashCode(), differentSudokuEntity.hashCode());
    }

    @Test
    void hashcodePositiveTest() {
        System.out.println("Check sudoku entity (row) object hashcode "
                + "with identical sudoku entity object hashcode");
        SudokuEntity differentSudokuEntity = new SudokuRow();
        sudokuEntity.setSudokuField(0, 1);
        differentSudokuEntity.setSudokuField(0, 1);
        assertEquals(sudokuEntity.hashCode(), differentSudokuEntity.hashCode());
    }

    @Test
    void equalsNegativeTest() {
        System.out.println("Check sudoku entity (row) object "
                + "with null object and different sudoku entity object");
        SudokuEntity differentSudokuEntity = new SudokuRow();
        sudokuEntity.setSudokuField(0, 2);
        differentSudokuEntity.setSudokuField(0, 1);
        assertNotEquals(sudokuEntity, differentSudokuEntity);
        assertNotEquals(sudokuEntity, null);
    }

    @Test
    void verifyNegativeTest() {
        System.out.println("Set identical values to sudoku entity");
        sudokuEntity.setSudokuField(0, 1);
        sudokuEntity.setSudokuField(1, 1);
        assertFalse(sudokuEntity.verify());
    }

    @Test
    void verifyPositiveTest() {
        System.out.println("Set different values to sudoku entity");
        for (int indexAndValue = 0; indexAndValue < 9; indexAndValue++) {
            sudokuEntity.setSudokuField(indexAndValue, indexAndValue);
        }
        assertTrue(sudokuEntity.verify());
    }

    @Test
    void equalsPositiveTest() {
        System.out.println("Check sudoku entity object "
                + "with identical sudoku entity object");
        sudokuEntity.setSudokuField(0, 1);
        SudokuEntity sameReferenceSudokuEntity = sudokuEntity;
        SudokuEntity identicalSudokuEntity = new SudokuRow();
        identicalSudokuEntity.setSudokuField(0, 1);
        assertEquals(sudokuEntity, identicalSudokuEntity);
        assertEquals(sudokuEntity, sameReferenceSudokuEntity);
    }
}
