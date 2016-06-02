package by.bsu.testparallel.algorithms;

import by.bsu.testparallel.main.Main;

public class MatrixMultiply<T extends Number> implements Runnable {
    private T[][] leftMatrix;
    private T[][] rightMatrix;
    private int rowNumber;

    public MatrixMultiply() {
    }

    public MatrixMultiply(T[][] leftMatrix, T[][] rightMatrix, int rowNumber) {
        this.leftMatrix = leftMatrix;
        this.rightMatrix = rightMatrix;
        this.rowNumber = rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < leftMatrix.length; i++) { //stroka
            for (int j = 0; j < rightMatrix.length; j++) { //stolbec
                Main.mulResult[rowNumber][i] += leftMatrix[rowNumber][j].doubleValue() * rightMatrix[j][i].doubleValue();
            }
        }
    }
}