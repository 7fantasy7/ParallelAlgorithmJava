package by.bsu.parallel.main;

import by.bsu.parallel.algorithms.InitializeMatrix;
import by.bsu.parallel.algorithms.MatrixMultiply;
import by.bsu.parallel.algorithms.MaxLineElement;
import by.bsu.parallel.algorithms.ScalarVectorMultiply;
import by.bsu.parallel.core.WorkController;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static final int SIZE = 500;
    public static final int testsNumber = 10;
    public static final int threadNumber = 4;


    public static double[] result = new double[SIZE];
    public static double[][] mulResult = new double[SIZE][SIZE];

    public static long[] timeInit = new long[threadNumber];
    public static long[] timeMatrixMultiply = new long[threadNumber];
    public static long[] timeMaxLineElement = new long[threadNumber];
    public static long[] timeScalarVectorMultiply = new long[threadNumber];
    public static long timeStart;
    public static long timeEnd;

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[SIZE][SIZE];
        Integer[][] matrix2 = new Integer[SIZE][SIZE];
        Random rand = new Random();
        System.err.println("Benchmarking...");
        for (int q = 0; q < testsNumber; q++) { //количество тестов

            timeStart = System.nanoTime();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    matrix[i][j] = rand.nextInt();
                }
            }
            timeEnd = System.nanoTime();
            timeInit[0] += timeEnd - timeStart;

            for (int i = 2; i <= threadNumber; i++) {
                timeStart = System.nanoTime();
                WorkController workController = new WorkController(i);
                workController.doWorkMatrix(new InitializeMatrix(), matrix2);
                timeEnd = System.nanoTime();
                timeInit[i - 1] += timeEnd - timeStart;
            }

            timeStart = System.nanoTime();
            for (int i = 0; i < mulResult.length; i++) { // aRow
                for (int j = 0; j < mulResult.length; j++) { // bColumn
                    for (int k = 0; k < mulResult.length; k++) { // aColumn
                        mulResult[i][j] += matrix[i][k] * matrix2[k][j];
                    }
                }
            }
            timeEnd = System.nanoTime();
            timeMatrixMultiply[0] += timeEnd - timeStart;

            for (int i = 2; i <= threadNumber; i++) {
                for (double[] row : mulResult)
                    Arrays.fill(row, 0.0);
                timeStart = System.nanoTime();
                WorkController workController = new WorkController(i);
                workController.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
                timeEnd = System.nanoTime();
                timeMatrixMultiply[i - 1] += timeEnd - timeStart;
            }


            ///////////////////////////////////////////////////////////////////
            timeStart = System.nanoTime();
            double max;
            for (int i = 0; i < SIZE; i++) {
                max = Double.MIN_VALUE;
                for (int j = 0; j < SIZE; j++) {
                    if (max < matrix[i][j]) {
                        max = matrix[i][j];
                    }
                }
                result[i] = max;
            }
            timeEnd = System.nanoTime();
            timeMaxLineElement[0] += timeEnd - timeStart;

            for (int i = 2; i <= threadNumber; i++) {
                Arrays.fill(result, 0.0);
                timeStart = System.nanoTime();
                WorkController workController = new WorkController(2);
                workController.doWorkVector(new MaxLineElement(), matrix);
                timeEnd = System.nanoTime();
                timeMaxLineElement[i - 1] += timeEnd - timeStart;
            }

            ///////////////////////////////////////////////////////////
            Arrays.fill(result, 0.0);
            timeStart = System.nanoTime();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    result[i] += matrix[i][j] * matrix2[i][j];
                }
            }
            timeEnd = System.nanoTime();
            timeScalarVectorMultiply[0] += timeEnd - timeStart;

            for (int i = 2; i <= threadNumber; i++) {
                Arrays.fill(result, 0.0);
                timeStart = System.nanoTime();
                WorkController workController = new WorkController(2);
                workController.doWorkMatrix(new ScalarVectorMultiply(), matrix, matrix2);
                timeEnd = System.nanoTime();
                timeScalarVectorMultiply[i - 1] += timeEnd - timeStart;
            }
            System.err.println("Passed " + (q + 1) + " test...");
        }

        System.out.println();
        System.out.println("Matrix size: " + SIZE);
        System.out.println("=====================");
        System.out.println();
        System.out.println("-=-=-Matrix Initialisation-=-=-");
        for (int i = 0; i < threadNumber; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeInit[i] / testsNumber));
        }
        System.out.println();
        System.out.println("-=-=-Matrix Multiplication-=-=-");
        for (int i = 0; i < threadNumber; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeMatrixMultiply[i] / testsNumber));
        }
        System.out.println();
        System.out.println("-=-=-Max Line Element-=-=-");
        for (int i = 0; i < threadNumber; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeMaxLineElement[i] / testsNumber));
        }
        System.out.println();
        System.out.println("-=-=-Scalar Vector Multiply-=-=-");
        for (int i = 0; i < threadNumber; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeScalarVectorMultiply[i] / testsNumber));
        }
    }
}