public class FieldAdapter {
    private SudokuBoard sudokuBoard;
    private int row;
    private int column;

    public FieldAdapter(SudokuBoard sudokuBoard, int row, int column) {
        this.sudokuBoard = sudokuBoard;
        this.row = row;
        this.column = column;
    }

    public int getFieldValue() {
        return sudokuBoard.getValue(row, column);
    }

    public void setFieldValue(int value) {
        sudokuBoard.setValue(row, column, value);
    }
}
