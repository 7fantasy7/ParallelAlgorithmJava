package by.bsu.testparallel.algorithms;

import by.bsu.testparallel.main.Main;

public class ScalarVectorMultiply<T extends Number> implements Runnable {
    private T[] matrix1line;
    private T[] matrix2line;
    private int rowNumber;

    public ScalarVectorMultiply() {
    }

    public ScalarVectorMultiply(T[] matrix1line, T[] matrix2line, int rowNumber) {
        this.matrix1line = matrix1line;
        this.matrix2line = matrix2line;
        this.rowNumber = rowNumber;
    }

    public void setMatrix1line(T[] matrix1line) {
        this.matrix1line = matrix1line;
    }

    public void setMatrix2line(T[] matrix2line) {
        this.matrix2line = matrix2line;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public void run() {
        double result = 0;
        if (matrix1line.length != matrix2line.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < matrix1line.length; i++) {
            result += matrix1line[i].doubleValue() * matrix2line[i].doubleValue();
        }
        Main.result[rowNumber] = result;
    }
}
