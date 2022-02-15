/*
 * #%L
 * View
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

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class SudokuBoardViewController {
    @FXML
    GridPane board;
    private DifficultyLevel difficultyFromViewController =
            DifficultiesViewController.difficulty;
    private SudokuBoard sudokuBoardForGame = new SudokuBoard(new BacktrackingSudokuSolver());

    @FXML
    public void initialize() {
        sudokuBoardForGame.solveGame();
        eraseFields();
        fillFields();
    }

    public void eraseFields() {
        difficultyFromViewController.chooseLevel(sudokuBoardForGame, difficultyFromViewController);
    }

    private void fillFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                customizeTextField(textField, i, j);
                board.add(textField, j, i);
            }
        }
    }


    public void customizeTextField(TextField textField, int row, int column) {
        textField.setMinSize(60, 66);
        textField.setFont(Font.font(36));
        textField.setAlignment(Pos.CENTER);

        if (sudokuBoardForGame.getValue(row, column) != 0) {
            textField.setDisable(true);
            textField.setText(String.valueOf(sudokuBoardForGame.getValue(row, column)));
        } else if (sudokuBoardForGame.getValue(row, column) == 0) {
            textField.clear();
        }
    }


    @FXML
    protected void pressBack() throws IOException {
        SudokuGame.switchMode(SudokuGame.pathToMenu);
        difficultyFromViewController.refill();
    }

}
