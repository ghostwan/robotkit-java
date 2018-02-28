package com.ghostwan.robotsdk.sdk;

/**
 * Created by erwan on 27/02/2018.
 */

@FunctionalInterface
public interface Task extends Runnable{

    static Task parallel(Task ...tasks) {
        return () -> {
            for (Task task : tasks) {
                new Thread(task::run).start();
            }
        };
    }

    static Task iterative(Task ...tasks) {
        return () -> {
            for (Task task : tasks) {
                task.run();
            }
        };
    }

}
