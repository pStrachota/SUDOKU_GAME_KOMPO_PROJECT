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

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SudokuBoardViewController {

    private static final Logger logger = LogManager.getLogger(DifficultiesViewController.class);
    private final JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
    private final JavaBeanIntegerProperty[][] fieldProperty = new JavaBeanIntegerProperty[9][9];
    private final DifficultyLevel difficultyFromViewController =
            DifficultiesViewController.difficulty;
    StringConverter overridenConverter = new ModifiedStringConverter();
    @FXML
    TextField zapiszTextField;
    @FXML
    TextField wczytajTextField;
    @FXML
    GridPane board;
    private SudokuBoard sudokuBoardForGame = new SudokuBoard(new BacktrackingSudokuSolver());

    @FXML
    public void initialize() {
        logger.debug("Initializing sudoku game");
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
                changeBoardValue(textField);


                try {
                    fieldProperty[i][j] = builder
                            .bean(new FieldAdapter(sudokuBoardForGame, i, j))
                            .name("FieldValue")
                            .build();
                } catch (NoSuchMethodException e) {
                    logger.error(new UnknownMethodException(
                            UnknownMethodException.UNKNOWN_METHOD, e));
                }

                textField.textProperty().bindBidirectional(
                        fieldProperty[i][j], overridenConverter);

                customizeTextField(textField, i, j);
                board.add(textField, j, i);
            }
        }
    }

    public void changeBoardValue(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!validateFieldValue(newValue)) {
                Platform.runLater(textField::clear);
            }
        });
    }

    public void customizeTextField(TextField textField, int row, int column) {
        textField.setMinSize(60, 66);
        textField.setFont(Font.font(36));
        textField.setAlignment(Pos.CENTER);

        if (sudokuBoardForGame.getValue(row, column) != 0
                && !sudokuBoardForGame.getBooleanValue(row, column)) {
            textField.setDisable(true);
            textField.setText(String.valueOf(sudokuBoardForGame.getValue(row, column)));
        } else if (sudokuBoardForGame.getValue(row, column) == 0) {
            textField.clear();
        }
    }

    public boolean validateFieldValue(String fieldValue) {
        return fieldValue.length() == 1 && "123456789".contains(fieldValue);
    }

    @FXML
    public void onActionButtonCheck() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ResourceBundle.getBundle(
                "language", Locale.getDefault()).getString("game.result"));

        if (sudokuBoardForGame.checkBoard()) {
            logger.info("User won");
            alert.setContentText(ResourceBundle.getBundle(
                    "language", Locale.getDefault()).getString("user.win"));
            alert.showAndWait();
        } else {
            logger.info("User did not win");
            alert.setContentText(ResourceBundle.getBundle(
                    "language", Locale.getDefault()).getString("user.lose"));
            alert.showAndWait();
        }
    }

    public void saveGameAction() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files",
                        "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        logger.info("Saving sudoku to file: " + file.getAbsolutePath());


        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath())) {
            fileSudokuBoardDao.write(sudokuBoardForGame);
        } catch (WrongFileNameException e) {
            logger.error(new WrongFileNameException(
                    WrongFileNameException.FILE_IO_ERROR, e));
        } catch (NotInitialisedDaoException e) {
            logger.error(new NotInitialisedDaoException(
                    NotInitialisedDaoException.NULL_PASSED, e));
        } catch (WrongFileContentException e) {
            logger.error(new WrongFileContentException(
                    WrongFileContentException.WRONG_FILE_CONTENT, e));
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public void loadGameAction() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files",
                        "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());
        logger.info("Loading sudoku from file: " + file.getAbsolutePath());

        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getFileDao(file.getAbsolutePath())) {
            sudokuBoardForGame = fileSudokuBoardDao.read();
        } catch (WrongFileNameException e) {
            logger.error(new WrongFileNameException(WrongFileNameException.FILE_IO_ERROR, e));
        } catch (NotInitialisedDaoException e) {
            logger.error(new NotInitialisedDaoException(NotInitialisedDaoException.NULL_PASSED, e));
        } catch (WrongFileContentException e) {
            logger.error(new WrongFileContentException(
                    WrongFileContentException.WRONG_FILE_CONTENT, e));
        } catch (Exception e) {
            logger.error(e);
        }
        reloadGrid();
    }

    void reloadGrid() {
        logger.debug("Reloading sudoku game grid");
        board.getChildren().clear();
        fillFields();
    }

    @FXML
    protected void pressBack() {
        logger.debug("Switching to menu options");
        SudokuGame.switchMode(SudokuGame.pathToMenu);
        difficultyFromViewController.refill();
    }

    @FXML
    public int saveToDatabase() {
        String boardName = zapiszTextField.getText();

        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getJdbcDao(boardName)) {
            logger.info("Saving sudoku '" + boardName + "' to database");
            fileSudokuBoardDao.write(sudokuBoardForGame);
        } catch (NameAlreadyExistException e) {
            showErrorAlert("invalid.name", "duplicated.name");
            zapiszTextField.clear();
            logger.error(new NameAlreadyExistException(NameAlreadyExistException.DUPLICATED_NAME));
            return -1;
        } catch (WrongFileNameException e) {
            logger.error(new WrongFileNameException(WrongFileNameException.FILE_IO_ERROR));
        } catch (NotInitialisedDaoException e) {
            logger.error(new NotInitialisedDaoException(NotInitialisedDaoException.NULL_PASSED));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void showErrorAlert(String title, String context) {
        ResourceBundle resourceBundle =
                ResourceBundle.getBundle("Exceptions", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resourceBundle.getString(title));
        alert.setHeaderText(null);
        alert.setContentText(resourceBundle.getString(context));
        alert.showAndWait();
    }

    @FXML
    public void loadFromDatabase() {

        String boardName = wczytajTextField.getText();
        SudokuBoard testSudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try (Dao<SudokuBoard> fileSudokuBoardDao =
                     SudokuBoardDaoFactory.getJdbcDao(boardName)) {
            testSudokuBoard = fileSudokuBoardDao.read();
            sudokuBoardForGame = testSudokuBoard;
            reloadGrid();
        } catch (GivenSudokuNotExistException e) {
            logger.error(new GivenSudokuNotExistException(
                    GivenSudokuNotExistException.NAME_NOT_EXIST));
            showErrorAlert("invalid.name", "name.not.exist");
            zapiszTextField.clear();
        } catch (WrongFileContentException e) {
            logger.error(new WrongFileContentException(
                    WrongFileContentException.WRONG_FILE_CONTENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
