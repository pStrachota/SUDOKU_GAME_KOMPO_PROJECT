/*
 * #%L
 * KOMPO_PROJECT
 * %%
 * Copyright (C) 2021 - 2022 TUL
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

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class SudokuEntity {
    private final int entitySize = 9;
    private final List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

    public SudokuEntity() {
        for (int index = 0; index < entitySize; index++) {
            sudokuFields.set(index, new SudokuField());
        }
    }

    public void setSudokuField(int index, int value) {
        this.sudokuFields.get(index).setFieldValue(value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuFields", sudokuFields)
                .toString();
    }

    public boolean verify() {
        for (int field = 0; field < entitySize; field++) {
            for (int index = 0; index < entitySize; index++) {
                if (sudokuFields.get(field).getFieldValue()
                        == sudokuFields.get(index).getFieldValue()
                        && field != index) {
                    return false;
                }
            }
        }
        return true;
    }


}
