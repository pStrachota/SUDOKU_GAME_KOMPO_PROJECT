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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuFieldTest {

    SudokuField sudokuField;

    @BeforeEach
    void init() {
        sudokuField = new SudokuField();
    }

    @Test
    void getAndSetFieldValuePositiveTest() {
        System.out.println("Set sudoku field value");
        sudokuField.setFieldValue(1);
        assertEquals(sudokuField.getFieldValue(), 1);
    }

    @Test
    void getAndSetFieldValueNegativeTest() {
        System.out.println("When incorrect sudoku value throw Illegal argument exception");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sudokuField.setFieldValue((10)));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Incorrect sudoku value";
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void negativeHashcodeTest() {
        System.out.println("Check sudoku field object hashcode "
                + "with different sudoku field object hashcode");
        sudokuField.setFieldValue(1);
        SudokuField differentSudokuField = new SudokuField();
        differentSudokuField.setFieldValue(2);
        assertNotEquals(sudokuField.hashCode(), differentSudokuField.hashCode());
    }

    @Test
    void negativePositiveTest() {
        System.out.println("Check sudoku field object hashcode "
                + "with identical sudoku field object hashcode");
        sudokuField.setFieldValue(1);
        SudokuField differentSudokuField = new SudokuField();
        differentSudokuField.setFieldValue(1);
        assertEquals(sudokuField.hashCode(), differentSudokuField.hashCode());
    }


    @Test
    void negativeEqualsTest() {
        System.out.println("Check sudoku field object with null object "
                + "and different sudoku field object");
        sudokuField.setFieldValue(1);
        SudokuField differentSudokuField = new SudokuField();
        differentSudokuField.setFieldValue(2);
        assertNotEquals(sudokuField, differentSudokuField);
        assertNotEquals(sudokuField, null);
    }

    @Test
    void positiveEqualsTest() {
        System.out.println("Check sudoku field object "
                + "with identical sudoku field object");
        sudokuField.setFieldValue(1);
        SudokuField sameReferenceSudokuField = sudokuField;
        SudokuField identicalSudokuField = new SudokuField();
        identicalSudokuField.setFieldValue(1);
        assertEquals(sudokuField, identicalSudokuField);
        assertEquals(sudokuField, sameReferenceSudokuField);
    }

    @Test
    void cloneTest() {
        System.out.println("Check equals between sudokuField and sudokuFieldClone");
        try {
            SudokuField sudokuFieldClone = (SudokuField) sudokuField.clone();
            assertEquals(sudokuField, sudokuFieldClone);
            sudokuFieldClone.setFieldValue(1);
            assertNotEquals(sudokuField, sudokuFieldClone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


}
