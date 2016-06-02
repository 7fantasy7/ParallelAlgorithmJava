package by.bsu.parallel.main;

import by.bsu.parallel.algorithms.InitializeMatrix;
import by.bsu.parallel.algorithms.MatrixMultiply;
import by.bsu.parallel.algorithms.MaxLineElement;
import by.bsu.parallel.algorithms.ScalarVectorMultiply;
import by.bsu.parallel.core.WorkController;

import java.util.Random;

public class Main {

    public static final int SIZE = 500;
    public static double[] result = new double[SIZE];

    public static double[][] mulResult = new double[SIZE][SIZE];

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[SIZE][SIZE];
        Integer[][] matrix2 = new Integer[SIZE][SIZE];

        Random rand = new Random();

        System.out.println("Initializing matrix...");
        long startInit = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = rand.nextInt();
            }
        }
        long finishInit = System.nanoTime();
        System.out.println("POSLEDOV " + (finishInit - startInit));


        long start4Init = System.nanoTime();
        WorkController workControllerInit = new WorkController(4);
        workControllerInit.doWorkMatrix(new InitializeMatrix(), matrix2);
        long finish4Init = System.nanoTime();
        System.out.println("2 POTOKA " + (finish4Init - start4Init));

        System.out.println("Starting benchmark...");
        System.out.println();

        System.out.println("-=-=-Multiplication-=-=-");
        long start4 = System.nanoTime();
        for (int i = 0; i < mulResult.length; i++) { // aRow
            for (int j = 0; j < mulResult.length; j++) { // bColumn
                for (int k = 0; k < mulResult.length; k++) { // aColumn
                    mulResult[i][j] += matrix[i][k] * matrix2[k][j];
                }
            }
        }
        long finish4 = System.nanoTime();
        System.out.println("POSLEDOV " + (finish4 - start4));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }
        long start4Mult = System.nanoTime();
        WorkController workControllerMult = new WorkController(2);
        workControllerMult.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        long finish4Mult = System.nanoTime();
        System.out.println("2 POTOKA " + (finish4Mult - start4Mult));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }

        long start4Mult3 = System.nanoTime();
        WorkController workControllerMult3 = new WorkController(3);
        workControllerMult3.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        long finish4Mult3 = System.nanoTime();
        System.out.println("3 POTOKA " + (finish4Mult3 - start4Mult3));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }

        long start4Mult2 = System.nanoTime();
        WorkController workControllerMult2 = new WorkController(4);
        workControllerMult2.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        long finish4Mult2 = System.nanoTime();
        System.out.println("4 POTOKA " + (finish4Mult2 - start4Mult2));

        ///////////////////////////////////////////////////////////////////
        System.out.println();
        System.out.println("-=-=-Max Line Element-=-=-");
        long start2 = System.nanoTime();
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
        long finish2 = System.nanoTime();
        System.out.println("POSLEDOV " + (finish2 - start2));

        long start = System.nanoTime();
        WorkController workController = new WorkController(2);
        workController.doWorkVector(new MaxLineElement(), matrix);
        long finish = System.nanoTime();
        System.out.println("2 POTOKA " + (finish - start));
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        long start3 = System.nanoTime();
        WorkController workController3 = new WorkController(3);
        workController3.doWorkVector(new MaxLineElement(), matrix);
        long finish3 = System.nanoTime();
        System.out.println("3 POTOKA " + (finish3 - start3));
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        long start45 = System.nanoTime();
        WorkController workController4 = new WorkController(3);
        workController4.doWorkVector(new MaxLineElement(), matrix);
        long finish45 = System.nanoTime();
        System.out.println("4 POTOKA " + (finish45 - start45));
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        ///////////////////////////////////////////////////////////
        System.out.println();
        System.out.println("-=-=-Scalar Vector Multiply-=-=-");
        long startOdno = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result[i] += matrix[i][j] * matrix2[i][j];
            }
        }
        long finishOdno = System.nanoTime();
        System.out.println("POSLEDOV " + (finishOdno - startOdno));

        long startZero = System.nanoTime();
        WorkController workControllerz = new WorkController(2);
        ScalarVectorMultiply scalarVectorMultiply = new ScalarVectorMultiply();
        workControllerz.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
        long finishZero = System.nanoTime();
        System.out.println("2 POTOKA " + (finishZero - startZero));
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        long startOne = System.nanoTime();
        WorkController workControllerOne = new WorkController(3);
        workControllerOne.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
        long finishOne = System.nanoTime();
        System.out.println("3 POTOKA " + (finishOne - startOne));
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        long startTwo = System.nanoTime();
        WorkController workControllerTwo = new WorkController(4);
        workControllerTwo.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
        long finishTwo = System.nanoTime();
        System.out.println("4 POTOKA " + (finishTwo - startTwo));

        System.out.println();
        System.out.println("=======================");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " " + result[i]);
        }
        System.out.println("lenght: " + mulResult.length);
        System.out.println(mulResult[5][10]);

    }
}