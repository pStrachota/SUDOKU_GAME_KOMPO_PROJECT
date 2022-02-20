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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DifficultiesViewController {

    static DifficultyLevel difficulty;
    private static Logger logger = LogManager.getLogger(DifficultiesViewController.class);

    @FXML
    public void pressHard() throws IOException {
        logger.info("choosing hard difficulty level");
        difficulty = DifficultyLevel.Hard;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    public void pressMedium() throws IOException {
        logger.info("choosing medium difficulty level");
        difficulty = DifficultyLevel.Medium;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    public void pressEasy() throws IOException {
        logger.info("choosing easy difficulty level");
        difficulty = DifficultyLevel.Easy;
        SudokuGame.switchMode(SudokuGame.pathToGameMode);
    }

    @FXML
    void pressExit() {
        logger.info("Closing application");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void changeToEnglishAction() throws IOException {
        logger.info("Changing language to english");
        Locale.setDefault(new Locale("en"));
        SudokuGame.switchMode(SudokuGame.pathToMenu);

    }

    @FXML
    public void authorsInfoAction() {
        logger.info("Displaying author informations");
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
        logger.info("Changing language to polish");
        Locale.setDefault(new Locale("pl"));
        SudokuGame.switchMode(SudokuGame.pathToMenu);
    }

}
