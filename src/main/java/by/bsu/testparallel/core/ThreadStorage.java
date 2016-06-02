package by.bsu.testparallel.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadStorage {
    private ExecutorService service;

    public ThreadStorage(int threadCount) {
        service = Executors.newFixedThreadPool(threadCount);
    }

    public void executeWork(Runnable runnable){
        service.execute(runnable);
    }

    public ExecutorService getService() {
        return service;
    }
}
