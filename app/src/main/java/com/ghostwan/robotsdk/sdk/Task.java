package com.ghostwan.robotsdk.sdk;

/**
 * Created by erwan on 27/02/2018.
 */

@FunctionalInterface
public interface Task extends Runnable{

    static Task parallel(Task ...tasks) {
        return () -> {

        };
    }

    static Task iterative(Task ...tasks) {
        return null;
    }

}
