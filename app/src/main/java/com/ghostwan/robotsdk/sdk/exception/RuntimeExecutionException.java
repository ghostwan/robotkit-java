package com.ghostwan.robotsdk.sdk.exception;

import java.util.concurrent.ExecutionException;

/**
 * Created by erwan on 28/02/2018.
 */

public class RuntimeExecutionException extends RuntimeException {
    public RuntimeExecutionException(ExecutionException e) {
        super(e.getMessage());
    }
}
