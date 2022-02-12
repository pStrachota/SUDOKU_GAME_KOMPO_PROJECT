import java.util.Arrays;
import java.util.List;

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

    public boolean verify() {
        for (int field = 0; field < entitySize; field++) {
            for (int index = 0; index < entitySize; index++) {
                if (sudokuFields.get(field).getFieldValue() == sudokuFields.get(index).getFieldValue()
                        && field != index) {
                    return false;
                }
            }
        }
        return true;
    }


}
