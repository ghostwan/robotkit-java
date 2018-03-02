package com.ghostwan.robotsdk.sdk;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by epinault on 02/03/2018.
 */

public class Tasks {
    public static Task parallel(Task... tasks) {
        return () -> {
            List<Thread> threads = new ArrayList<>();
            for (Task task : tasks) {
                Thread thread = new Thread(task::execute);
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Log.e("Task", "error :", e);
                }
            }
        };
    }

    static Task iterative(Task ...tasks) {
        return () -> {
            for (Task task : tasks) {
                task.execute();
            }
        };
    }
}
