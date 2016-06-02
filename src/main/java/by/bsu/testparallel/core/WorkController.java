package by.bsu.testparallel.core;

import by.bsu.testparallel.algorithms.InitializeMatrix;
import by.bsu.testparallel.algorithms.MatrixMultiply;
import by.bsu.testparallel.algorithms.MaxLineElement;
import by.bsu.testparallel.algorithms.ScalarVectorMultiply;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class WorkController<T extends Number> {
    ThreadStorage threadStorage;

    public WorkController(int threadCount) {
        this.threadStorage = new ThreadStorage(threadCount);
    }

    public void doWorkMatrix(Runnable runnable, T[][]... matrixes) {
        try {
            if (runnable instanceof ScalarVectorMultiply) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    threadStorage.executeWork(new ScalarVectorMultiply(matrixes[0][i], matrixes[1][i], i));
                }
            } else if (runnable instanceof InitializeMatrix) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    threadStorage.executeWork(new InitializeMatrix(matrixes[0], i));
                }
            } else if (runnable instanceof MatrixMultiply) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    threadStorage.executeWork(new MatrixMultiply(matrixes[0], matrixes[1], i));
                }
            }
        } catch (RejectedExecutionException e) {
        }

        threadStorage.getService().shutdown();
        try {
            while (!threadStorage.getService().awaitTermination(24L, TimeUnit.HOURS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
        }
    }

    public void doWorkVector(Runnable runnable, T[]... matrixes) {
        try {
            if (runnable instanceof MaxLineElement) {
                for (int i = 0; i < matrixes[0].length; i++) {
                    threadStorage.executeWork(new MaxLineElement(matrixes[i], i));
                }
            }
        } catch (RejectedExecutionException e) {
        }

        threadStorage.getService().shutdown();
        try {
            while (!threadStorage.getService().awaitTermination(24L, TimeUnit.HOURS)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
        }
    }
}
