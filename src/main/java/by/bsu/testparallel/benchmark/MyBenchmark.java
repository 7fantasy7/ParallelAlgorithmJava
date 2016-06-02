package by.bsu.testparallel.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

@State(Scope.Thread)
public class MyBenchmark {

    public static final int SIZE = 1000;
    Integer[][] matrix;
    Integer[][] matrix2;

    @Setup
    public void setup(){
        matrix = new Integer[SIZE][SIZE];
        matrix2 = new Integer[SIZE][SIZE];

        Random rand = new Random();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = rand.nextInt();
                matrix2[i][j] = rand.nextInt();
            }
        }

//        ThreadStorage.THREAD_COUNT = 2;
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void testMethod() {
        double start = System.nanoTime();
//        WorkController workController = new WorkController();
//        workController.doWork(matrix, matrix2);
        double finish = System.nanoTime();
        System.out.println("2 POTOKA " + (finish - start));
    }

}
