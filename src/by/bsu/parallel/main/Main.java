package by.bsu.parallel.main;

import by.bsu.parallel.algorithms.InitializeMatrix;
import by.bsu.parallel.algorithms.MatrixMultiply;
import by.bsu.parallel.algorithms.MaxLineElement;
import by.bsu.parallel.algorithms.ScalarVectorMultiply;
import by.bsu.parallel.core.WorkController;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static final int SIZE = 1000;             //размерность матрицы
    public static final int TEST_NUMBER = 10;       //количество тестов
    public static final int THREAD_NUMBER = 4;      //количество потоков

    public static double[] result = new double[SIZE];
    public static double[][] mulResult = new double[SIZE][SIZE];

    public static long[] timeInit = new long[THREAD_NUMBER];
    public static long[] timeMatrixMultiply = new long[THREAD_NUMBER];
    public static long[] timeMaxLineElement = new long[THREAD_NUMBER];
    public static long[] timeScalarVectorMultiply = new long[THREAD_NUMBER];
    public static long timeStart;                   //время начала
    public static long timeEnd;                     //время конца

    public static void main(String[] args) {
        Integer[][] firstMatrix = new Integer[SIZE][SIZE];   //Входные данные могут быть любого типа, поддерживаемого классим Number
        Integer[][] secondMatrix = new Integer[SIZE][SIZE];
        Random rand = new Random();
        System.err.println("Benchmarking...");
        for (int q = 0; q < TEST_NUMBER; q++) { //цикл для тестирования

            ////////////////////////////////////////////////////////////////////////////////////
            //Последовательные алгоритмы
            ////////////////////////////////////////////////////////////////////////////////////

            //Инициализация матрицы случайными значениями
            timeStart = System.nanoTime();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    firstMatrix[i][j] = rand.nextInt();
                }
            }
            timeEnd = System.nanoTime();
            timeInit[0] += timeEnd - timeStart;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    secondMatrix[i][j] = rand.nextInt();
                }
            }

            //Умножение матриц
            timeStart = System.nanoTime();
            for (int i = 0; i < mulResult.length; i++) { // aRow
                for (int j = 0; j < mulResult.length; j++) { // bColumn
                    for (int k = 0; k < mulResult.length; k++) { // aColumn
                        mulResult[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                    }
                }
            }
            timeEnd = System.nanoTime();
            timeMatrixMultiply[0] += timeEnd - timeStart;

            //Нахождение максимального элемента для каждой строки матрицы
            timeStart = System.nanoTime();
            double max;
            for (int i = 0; i < SIZE; i++) {
                max = Double.MIN_VALUE;
                for (int j = 0; j < SIZE; j++) {
                    if (max < firstMatrix[i][j]) {
                        max = firstMatrix[i][j];
                    }
                }
                result[i] = max;
            }
            timeEnd = System.nanoTime();
            timeMaxLineElement[0] += timeEnd - timeStart;

            //Скалярное умножение векторов
            Arrays.fill(result, 0.0);
            timeStart = System.nanoTime();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    result[i] += firstMatrix[i][j] * secondMatrix[i][j];
                }
            }
            timeEnd = System.nanoTime();
            timeScalarVectorMultiply[0] += timeEnd - timeStart;

            ////////////////////////////////////////////////////////////////////////////////////
            //Многопоточная реализация алгоритмов
            ////////////////////////////////////////////////////////////////////////////////////

            for (int i = 2; i <= THREAD_NUMBER; i++) { //тестирование на разном количестве потоков

                //Инициализация матрицы случайными значениями
                timeStart = System.nanoTime();
                WorkController workControllerInit = new WorkController(i);
                workControllerInit.doWorkMatrix(new InitializeMatrix(), secondMatrix);
                timeEnd = System.nanoTime();
                timeInit[i - 1] += timeEnd - timeStart;

                //Умножение матриц
                for (double[] row : mulResult)
                    Arrays.fill(row, 0.0);
                timeStart = System.nanoTime();
                WorkController workControllerMatrixMultiply = new WorkController(i);
                workControllerMatrixMultiply.doWorkMatrix(new MatrixMultiply(), firstMatrix, secondMatrix);
                timeEnd = System.nanoTime();
                timeMatrixMultiply[i - 1] += timeEnd - timeStart;

                //Нахождение максимального элемента для каждой строки матрицы
                Arrays.fill(result, 0.0);
                timeStart = System.nanoTime();
                WorkController workControllerMaxLineElement = new WorkController(i);
                workControllerMaxLineElement.doWorkVector(new MaxLineElement(), firstMatrix);
                timeEnd = System.nanoTime();
                timeMaxLineElement[i - 1] += timeEnd - timeStart;

                //Скалярное умножение векторов
                Arrays.fill(result, 0.0);
                timeStart = System.nanoTime();
                WorkController workControllerScalarVectorMultiply = new WorkController(i);
                workControllerScalarVectorMultiply.doWorkMatrix(new ScalarVectorMultiply(), firstMatrix, secondMatrix);
                timeEnd = System.nanoTime();
                timeScalarVectorMultiply[i - 1] += timeEnd - timeStart;
            }

            System.err.println("Passed " + (q + 1) + " test...");
        }

        System.out.println();
        System.out.println("=====================");
        System.out.println("Matrix size: " + SIZE);
        System.out.println("=====================");
        System.out.println();
        System.out.println("-=-=-Matrix Initialisation-=-=-");
        for (int i = 0; i < THREAD_NUMBER; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeInit[i] / TEST_NUMBER));
        }
        System.out.println();
        System.out.println("-=-=-Matrix Multiplication-=-=-");
        for (int i = 0; i < THREAD_NUMBER; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeMatrixMultiply[i] / TEST_NUMBER));
        }
        System.out.println();
        System.out.println("-=-=-Max Line Element-=-=-");
        for (int i = 0; i < THREAD_NUMBER; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeMaxLineElement[i] / TEST_NUMBER));
        }
        System.out.println();
        System.out.println("-=-=-Scalar Vector Multiply-=-=-");
        for (int i = 0; i < THREAD_NUMBER; i++) {
            System.out.println((i + 1) + " Threaded: " + (timeScalarVectorMultiply[i] / TEST_NUMBER));
        }
    }
}