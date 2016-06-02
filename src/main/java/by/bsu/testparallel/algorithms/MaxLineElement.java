package by.bsu.testparallel.algorithms;

import by.bsu.testparallel.main.Main;

public class MaxLineElement<T extends Number> implements Runnable {
    private T[] matrix1line;
    private int rowNumber;

    public MaxLineElement() {
    }

    public MaxLineElement(T[] matrix1line, int rowNumber) {
        this.matrix1line = matrix1line;
        this.rowNumber = rowNumber;
    }

    public void setMatrix1line(T[] matrix1line) {
        this.matrix1line = matrix1line;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public void run() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < matrix1line.length; i++) {
            if (max < matrix1line[i].doubleValue()) {
                max = matrix1line[i].doubleValue();
            }
        }
        Main.result[rowNumber] = max;
    }
}
