/*
 * #%L
 * KOMPO_PROJECT
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String fileName;

    public FileSudokuBoardDao(String fileName)
            throws NotInitialisedDaoException {
        if (fileName == null) {
            throw new NotInitialisedDaoException(NotInitialisedDaoException.NULL_PASSED);
        } else {
            this.fileName = fileName;
        }
    }

    @Override
    public SudokuBoard read() throws WrongFileContentException,
            WrongFileNameException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (SudokuBoard) objectInputStream.readObject();
        } catch (IOException e) {
            throw new WrongFileNameException(WrongFileNameException.FILE_IO_ERROR, e);
        } catch (ClassNotFoundException e) {
            throw new WrongFileContentException(WrongFileContentException.WRONG_FILE_CONTENT, e);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws WrongFileNameException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            throw new WrongFileNameException(WrongFileNameException.FILE_IO_ERROR, e);
        }
    }

    @Override
    public void close() throws Exception {
        new FileInputStream(fileName).close();
        new ObjectInputStream(new FileInputStream(fileName)).close();
    }
}
