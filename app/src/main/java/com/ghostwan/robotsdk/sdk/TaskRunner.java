package com.ghostwan.robotsdk.sdk;

import com.ghostwan.robotsdk.sdk.exception.TaskInterruptedException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by erwan on 27/02/2018.
 */

public class TaskRunner {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Future<?> currentFuture;


    public void run(Task task){
        try {
            executorService.submit(task).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new TaskInterruptedException();
        }
    }

    public void start(Task task) {
        currentFuture = executorService.submit(task);
    }

    public void stopCurrent() {
        if(currentFuture != null) {
            currentFuture.cancel(true);
            currentFuture = null;
        }
    }

}
