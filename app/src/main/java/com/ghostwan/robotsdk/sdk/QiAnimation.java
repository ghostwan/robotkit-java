package com.ghostwan.robotsdk.sdk;

import android.support.annotation.RawRes;
import com.aldebaran.qi.sdk.object.actuation.Animation;

import java.util.List;
import java.util.Map;

/**
 * Created by erwan on 28/02/2018.
 */

public class QiAnimation {

    private Integer res;
    private Long duration;
    private Map<String, List<Long>> labels;
    private Animation robotAnimation;

    public QiAnimation(@RawRes Integer res) {
        this.res = res;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }

    public void setLabels(Map<String, List<Long>> labels) {
        this.labels = labels;
    }

    public Map<String, List<Long>> getLabels() {
        return labels;
    }

    public void setRobotAnimation(Animation robotAnimation) {
        this.robotAnimation = robotAnimation;
    }

    public Animation getRobotAnimation() {
        return robotAnimation;
    }
}
