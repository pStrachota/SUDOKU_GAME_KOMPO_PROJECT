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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public enum DifficultyLevel {


    Easy(12), Medium(24), Hard(36);

    private final Set<FieldCoordinates> fieldsToRemove = new HashSet<>();
    private Random random = new Random();
    private int cellsToDelete;

    DifficultyLevel(int cellsToDelete) {
        this.cellsToDelete = cellsToDelete;
    }

    private void fillSetWithRandomPositions() {

        for (int i = 0; i < cellsToDelete; i++) {
            boolean isAdded = false;
            if (isAdded) {
                i++;
            }
                int axisX = random.nextInt(9);
                int axisY = random.nextInt(9);
                isAdded = fieldsToRemove.add(new FieldCoordinates(axisX, axisY));
        }
    }

    public SudokuBoard chooseLevel(SudokuBoard sudokuBoard, DifficultyLevel difficulty) {

        fillSetWithRandomPositions();

        for (FieldCoordinates it : fieldsToRemove) {
            sudokuBoard.setValue(it.getFieldX(), it.getFieldY(), 0);
        }
        return sudokuBoard;

    }

    public void refill() {
        fieldsToRemove.clear();
    }

}