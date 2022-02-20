/*
 * #%L
 * ModelProject
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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcSudokuBoardDaoTest {


    @Test
    void givenNameAlreadyExistTest() {
        System.out.println("if given name already exist in database, throw error");
        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getJdbcDao("pierwszeSudoku")) {
            fileSudokuBoardDao.write(new SudokuBoard(new BacktrackingSudokuSolver()));
        } catch (NameAlreadyExistException e) {
            String actualMessage = e.toString();
            String expectedMessage =
                    "NameAlreadyExistException: "
                            + "Sudoku o podanej nazwie już istnieje w bazie danych";
            assertEquals(actualMessage, expectedMessage);
        } catch (NotInitialisedDaoException notInitialisedDaoException) {
            notInitialisedDaoException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Test
    void nullFileNameTest() {
        System.out.println("null pass to 'getJdbcDao'");
        try (Dao<SudokuBoard> jdbcSudokuBoardDaoTest =
                     SudokuBoardDaoFactory.getJdbcDao(null)) {
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
    void loadingNonExistingSudokuExceptionTest() {
        System.out.println("if given name is not in database, throw error");
        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getJdbcDao("notExist")) {
            SudokuBoard exceptionWillBeHere = fileSudokuBoardDao.read();
        } catch (GivenSudokuNotExistException e) {
            String actualMessage = e.toString();
            String expectedMessage =
                    "GivenSudokuNotExistException: Nie znaleziono planszy o podanej nazwie";
            assertEquals(actualMessage, expectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Disabled("any time more than one generates error, due to already existing sudoku")
    @Test
    void readAndWriteToDatabasePositiveTest() {
        System.out.println("test for saving and loading sudoku from database");
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getJdbcDao("jdbcTest")) {
            fileSudokuBoardDao.write(sudokuBoard);
            SudokuBoard sudokuBoardFromDatabase = fileSudokuBoardDao.read();
            assertEquals(sudokuBoard, sudokuBoardFromDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
