import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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


public class SudokuGame extends Application {

    public static final String pathToGameMode = "sudoku-board-view.fxml";
    public static final String pathToMenu = "difficulties-view.fxml";
    private static Scene scene = new Scene(new Pane());

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        switchMode(pathToMenu);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

    public static void switchMode(String pathToFxmlFile) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language");
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        SudokuGame.class.getResource(pathToFxmlFile), resourceBundle);
        scene.setRoot(fxmlLoader.load());
    }
}
