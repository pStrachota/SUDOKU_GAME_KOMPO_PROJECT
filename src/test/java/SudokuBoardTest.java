import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
}