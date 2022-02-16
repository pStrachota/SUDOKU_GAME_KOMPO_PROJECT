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
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class DifficultiesViewController {

    static DifficultyLevel difficulty;


    @FXML
    public void pressHard() throws IOException {
        difficulty = DifficultyLevel.Hard;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    public void pressMedium() throws IOException {
        difficulty = DifficultyLevel.Medium;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    public void pressEasy() throws IOException {
        difficulty = DifficultyLevel.Easy;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    void pressExit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void changeToEnglishAction() throws IOException {
        Locale.setDefault(new Locale("en"));
        SudokuGame.switchMode(SudokuGame.pathToMenu);

    }

    @FXML
    public void authorsInfoAction() {
        ResourceBundle
                resourceBundle =
                ResourceBundle.getBundle("Author", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(resourceBundle.getString("2. "));
        alert.setHeaderText(null);
        alert.setContentText(
                resourceBundle.getString("1. "));
        alert.showAndWait();
    }

    @FXML
    public void changeToPolishAction() throws IOException {
        Locale.setDefault(new Locale("pl"));
        SudokuGame.switchMode(SudokuGame.pathToMenu);
    }

}
