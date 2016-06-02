package by.bsu.testparallel.main;

import by.bsu.testparallel.algorithms.InitializeMatrix;
import by.bsu.testparallel.algorithms.MatrixMultiply;
import by.bsu.testparallel.core.WorkController;

import java.util.Random;

public class Main {

    public static final int SIZE = 1000;
    public static double[] result = new double[SIZE];

    public static double[][] mulResult = new double[SIZE][SIZE];

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[SIZE][SIZE];
        Integer[][] matrix2 = new Integer[SIZE][SIZE];

//        double start3 = System.nanoTime();
//        WorkController workController3 = new WorkController(1);
//        workController3.doWorkMatrix(new MatrixMultiply(), matrix2, matrix);
//        double finish3 = System.nanoTime();
//        System.out.println("1 POTOKA " + (finish3 - start3));
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(mulResult[i][j] + " ");
//            }
//            System.out.println();
//        }

        Random rand = new Random();

        System.out.println("Initializing matrix...");
        double startInit = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = rand.nextInt();
            }
        }
        double finishInit = System.nanoTime();
        System.out.println("1 POTOK " + (finishInit - startInit));


        double start4Init = System.nanoTime();
        WorkController workControllerInit = new WorkController(4);
        workControllerInit.doWorkMatrix(new InitializeMatrix(), matrix2);
        double finish4Init = System.nanoTime();
        System.out.println("2 POTOKA " + (finish4Init - start4Init));


        System.out.println("Multiplication");
        double start4 = System.nanoTime();
        for (int i = 0; i < mulResult.length; i++) { // aRow
            for (int j = 0; j < mulResult.length; j++) { // bColumn
                for (int k = 0; k < mulResult.length; k++) { // aColumn
                    mulResult[i][j] += matrix[i][k] * matrix2[k][j];
                }
            }
        }
        double finish4 = System.nanoTime();
        System.out.println("1 POTOK " + (finish4 - start4));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }
        System.out.println("Multiplication");
        double start4Mult = System.nanoTime();
        WorkController workControllerMult = new WorkController(2);
        workControllerMult.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        double finish4Mult = System.nanoTime();
        System.out.println("2 POTOKA " + (finish4Mult - start4Mult));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }

        System.out.println("Multiplication");
        double start4Mult3 = System.nanoTime();
        WorkController workControllerMult3 = new WorkController(3);
        workControllerMult3.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        double finish4Mult3 = System.nanoTime();
        System.out.println("3 POTOKA " + (finish4Mult3 - start4Mult3));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mulResult[i][j] = 0;
            }
        }

        System.out.println("Multiplication");
        double start4Mult2 = System.nanoTime();
        WorkController workControllerMult2 = new WorkController(4);
        workControllerMult2.doWorkMatrix(new MatrixMultiply(), matrix, matrix2);
        double finish4Mult2 = System.nanoTime();
        System.out.println("4 POTOKA " + (finish4Mult2 - start4Mult2));

        System.out.println("lenght: " + mulResult.length);
        System.out.println(mulResult[5][10]);


//
//        ///////////////////////////////////////////////////////////////////
//        System.out.println("Starting benchmark...");
//        System.out.println();
//        System.out.println("-=-=-Max Line Element-=-=-");
//        double start2 = System.nanoTime();
//        double max;
//        for (int i = 0; i < SIZE; i++) {
//            max = Double.MIN_VALUE;
//            for (int j = 0; j < SIZE; j++) {
//                if (max < matrix[i][j]) {
//                    max = matrix[i][j];
//                }
//            }
//            result[i] = max;
//        }
//        double finish2 = System.nanoTime();
//        System.out.println("1 POTOK " + (finish2 - start2));
//
//        double start = System.nanoTime();
//        WorkController workController = new WorkController(2);
//        workController.doWorkVector(new MaxLineElement(), matrix);
//        double finish = System.nanoTime();
//        System.out.println("2 POTOKA " + (finish - start));
//        for (int i = 0; i < result.length; i++) {
//            result[i] = 0;
//        }
//
//        double start3 = System.nanoTime();
//        WorkController workController3 = new WorkController(3);
//        workController3.doWorkVector(new MaxLineElement(), matrix);
//        double finish3 = System.nanoTime();
//        System.out.println("3 POTOKA " + (finish3 - start3));
//        for (int i = 0; i < result.length; i++) {
//            result[i] = 0;
//        }
//
//        double start4 = System.nanoTime();
//        WorkController workController4 = new WorkController(3);
//        workController4.doWorkVector(new MaxLineElement(), matrix);
//        double finish4 = System.nanoTime();
//        System.out.println("4 POTOKA " + (finish4 - start4));
//        for (int i = 0; i < result.length; i++) {
//            result[i] = 0;
//        }
//
//        ///////////////////////////////////////////////////////////
//        System.out.println();
//        System.out.println("-=-=-Scalar Vector Multiply-=-=-");
//        double startOdno = System.nanoTime();
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                result[i] += matrix[i][j] * matrix2[i][j];
//            }
//        }
//        double finishOdno = System.nanoTime();
//        System.out.println("1 POTOK " + (finishOdno - startOdno));
//
//        double startZero = System.nanoTime();
//        WorkController workControllerz = new WorkController(2);
//        ScalarVectorMultiply scalarVectorMultiply = new ScalarVectorMultiply();
//        workControllerz.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
//        double finishZero = System.nanoTime();
//        System.out.println("2 POTOKA " + (finishZero - startZero));
//        for (int i = 0; i < result.length; i++) {
//            result[i] = 0;
//        }
//
//        double startOne = System.nanoTime();
//        WorkController workControllerOne = new WorkController(3);
//        workControllerOne.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
//        double finishOne = System.nanoTime();
//        System.out.println("3 POTOKA " + (finishOne - startOne));
//        for (int i = 0; i < result.length; i++) {
//            result[i] = 0;
//        }
//
//        double startTwo = System.nanoTime();
//        WorkController workControllerTwo = new WorkController(4);
//        workControllerTwo.doWorkMatrix(scalarVectorMultiply, matrix, matrix2);
//        double finishTwo = System.nanoTime();
//        System.out.println("4 POTOKA " + (finishTwo - startTwo));
//
//        System.out.println();
//        System.out.println("=======================");
//        for (int i = 0; i < 5; i++) {
//            System.out.println(i + " " + result[i]);
//        }

    }
}