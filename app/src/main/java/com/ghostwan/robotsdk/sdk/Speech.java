package com.ghostwan.robotsdk.sdk;

/**
 * Created by erwan on 28/02/2018.
 */

public class Speech {

    private String data;

    public Speech(String data) {
        this.data = data;
    }

    public void setOnStartListener(Runnable runnable) {

    }

    public String getData() {
        return data;
    }
}
