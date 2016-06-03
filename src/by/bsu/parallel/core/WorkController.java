package by.bsu.parallel.core;

import by.bsu.parallel.algorithms.InitializeMatrix;
import by.bsu.parallel.algorithms.MatrixMultiply;
import by.bsu.parallel.algorithms.MaxLineElement;
import by.bsu.parallel.algorithms.ScalarVectorMultiply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class WorkController<T extends Number> {
    private ExecutorService service;

    public WorkController(int threadCount) {
        this.service = Executors.newFixedThreadPool(threadCount);
    }

    @SafeVarargs
    public final void doWorkMatrix(Runnable runnable, T[][]... matrixes) {
        try {
            if (runnable.getClass() == ScalarVectorMultiply.class) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    service.execute(new ScalarVectorMultiply<>(matrixes[0][i], matrixes[1][i], i));
                }
            } else if (runnable.getClass() == InitializeMatrix.class) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    service.execute(new InitializeMatrix<>(matrixes[0], i));
                }
            } else if (runnable.getClass() == MatrixMultiply.class) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    service.execute(new MatrixMultiply<>(matrixes[0], matrixes[1], i));
                }
            }
        } catch (RejectedExecutionException e) {
        }

        service.shutdown();
        try {
            while (!service.awaitTermination(2L, TimeUnit.SECONDS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
        }
    }

    @SafeVarargs
    public final void doWorkVector(Runnable runnable, T[]... matrixes) {
        try {
            if (runnable.getClass() == MaxLineElement.class) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    service.execute(new MaxLineElement<>(matrixes[i], i));
                }
            }
        } catch (RejectedExecutionException e) {
        }

        service.shutdown();
        try {
            while (!service.awaitTermination(2L, TimeUnit.SECONDS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
        }
    }
}
