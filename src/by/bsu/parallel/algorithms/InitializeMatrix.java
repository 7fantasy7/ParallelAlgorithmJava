package by.bsu.parallel.algorithms;

import java.util.Random;

public class InitializeMatrix<T extends Number> implements Runnable {
    private T[][] matrix;
    private int rowNumber;

    public InitializeMatrix() {
    }

    public InitializeMatrix(T[][] matrix, int rowNumber) {
        this.matrix = matrix;
        this.rowNumber = rowNumber;
    }

    public void setMatrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int j = 0; j < matrix.length; j++) {
            matrix[rowNumber][j] = (T) Integer.valueOf(rand.nextInt());
        }
    }
}
