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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private static final String URL = "jdbc:mysql://localhost/mysql?"
            + "user=root&password=root";
    private final String fileName;

    public JdbcSudokuBoardDao(String fileName)
            throws NotInitialisedDaoException {
        if (fileName == null) {
            throw new NotInitialisedDaoException(
                    NotInitialisedDaoException.NULL_PASSED, new IllegalArgumentException());
        } else {
            this.fileName = fileName;
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public SudokuBoard read() throws WrongFileNameException, WrongFileContentException,
            GivenSudokuNotExistException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String sudokuFieldsString = null;
        String sudokuBooleanValuesString = null;
        int[] sudokuFieldsTab = new int[81];
        boolean[] sudokuFieldsBoolean = new boolean[81];
        String testName = null;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT sudoku_name, sudoku_fields, sudoku_boolean_values from sudoku_boards"
                             + " where sudoku_name = ?")) {

            statement.execute("use Sudoku_Games");
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                testName = resultSet.getString(1);
                sudokuFieldsString = resultSet.getString(2);
                sudokuBooleanValuesString = resultSet.getString(3);
            }

            if (testName == null) {
                throw new GivenSudokuNotExistException(
                        GivenSudokuNotExistException.NAME_NOT_EXIST,
                        new NullPointerException());
            }

            for (int i = 0; i < 81; i++) {
                sudokuFieldsTab[i] = Character.getNumericValue(sudokuFieldsString.charAt(i));
                int index = Character.getNumericValue(sudokuBooleanValuesString.charAt(i));
                if (index == 0) {
                    sudokuFieldsBoolean[i] = false;
                } else {
                    sudokuFieldsBoolean[i] = true;
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuBoard.setBooleanValue(i, j, sudokuFieldsBoolean[i * 9 + j]);
                    sudokuBoard.setValue(i, j, sudokuFieldsTab[i * 9 + j]);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws WrongFileNameException, NameAlreadyExistException {
        String testName = null;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT sudoku_name, sudoku_fields from sudoku_boards"
                             + " where sudoku_name = ?")) {

            statement.execute("use Sudoku_Games");
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                testName = resultSet.getString(1);
            }

            if (testName != null) {
                throw new NameAlreadyExistException(
                        NameAlreadyExistException.DUPLICATED_NAME, new IllegalArgumentException());
            }

            try (Connection connection2 = DriverManager.getConnection(URL);
                 Statement statement2 = connection2.createStatement();
                 PreparedStatement preparedStatement2 = connection2
                         .prepareStatement("INSERT INTO sudoku_boards values (?, ?, ?)")) {
                statement2.execute("use Sudoku_Games");
                preparedStatement2.setString(1, fileName);
                preparedStatement2.setString(2, obj.toString());
                preparedStatement2.setString(3, obj.booleanValuesToString());
                preparedStatement2.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
