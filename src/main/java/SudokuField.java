public class SudokuField {

    private int value = 0;


    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
}
