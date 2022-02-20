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

import org.junit.jupiter.api.Test;

class FileSudokuBoardDaoTest {


    @Test
    void nonExistingFileTest() {
        try (Dao<SudokuBoard> fileSudokuBoardDaoTest =
                     SudokuBoardDaoFactory.getFileDao("nothing.txt")) {
            System.out.println("Non existing file name pass to 'getFileDao'");
            fileSudokuBoardDaoTest.read();
        } catch (WrongFileNameException e) {
            String actualMessage = e.toString();
            String expectedMessage =
                    "WrongFileNameException: Zła nazwa pliku lub plik nie istnieje";
            assertEquals(actualMessage, expectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void nullFileNameTest() {
        System.out.println("Non existing file name pass to 'getFileDao'");
        try (Dao<SudokuBoard> fileSudokuBoardDaoTest =
                     SudokuBoardDaoFactory.getFileDao(null)) {
            System.out.println("null file name test");
        } catch (NotInitialisedDaoException e) {
            String actualMessage = e.toString();
            String expectedMessage = "NotInitialisedDaoException: Dao nie zostało zainicjalizowane";
            assertEquals(actualMessage, expectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void incorrectFileNameTest() {
        try (Dao<SudokuBoard> fileSudokuBoardDaoTest =
                     SudokuBoardDaoFactory.getFileDao("///'");) {
            System.out.println("Incorrect file name pass to 'getFileDao'");
            fileSudokuBoardDaoTest.read();
        } catch (WrongFileNameException e) {
            String actualMessage = e.toString();
            String expectedMessage = "WrongFileNameException: "
                    + "Zła nazwa pliku lub plik nie istnieje";
            assertEquals(actualMessage, expectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readAndWritePositiveTest() {
        System.out.println("Serialize and deserialize sudoku board using testDao.txt");
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        try (Dao<SudokuBoard> fileSudokuBoardDaoTest =
                     SudokuBoardDaoFactory.getFileDao("testDao.txt")) {
            fileSudokuBoardDaoTest.write(sudokuBoard);
            SudokuBoard sudokuBoardFromFile = fileSudokuBoardDaoTest.read();
            assertEquals(sudokuBoard, sudokuBoardFromFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
