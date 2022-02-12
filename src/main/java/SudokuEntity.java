public abstract class SudokuEntity {
    private SudokuField[] sudokuFields = new SudokuField[9];

    public SudokuEntity() {
        for (int field = 0; field < 9; field++) {
            sudokuFields[field] = new SudokuField();
        }
    }

        public void setSudokuField(int index, int value) {
        this.sudokuFields[index].setFieldValue(value);
    }

    public boolean verify() {
        for (int field = 0; field < 9; field++) {
            for (int index = 0; index < 9; index++) {
                if (sudokuFields[field].getFieldValue() == sudokuFields[index].getFieldValue()
                        && field != index) {
                    return false;
                }
            }
        }
        return true;
    }


}
