package com.ghostwan.robotsdk.sdk;


import android.app.Activity;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.util.Log;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Promise;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.*;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.ghostwan.robotsdk.sdk.exception.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by erwan on 27/02/2018.
 */

public class Robot {
    public static final Integer AUTONOMOUS_BLINKING = 1;
    private static final String TAG = "Robot";
    private QiContext qiContext;
    private Promise<Void> connectionPromise;
    private Future sayFuture;
    private Future animateFuture;
    private Future gotoFuture;
    private Future<ListenResult> listenFuture;


    public Robot(String address) {
    }


    private void checkThread() {
        if(Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new LongOperationOnUiThreadException();
        }
    }

    private void checkReady() {
        checkThread();
        if(qiContext == null)
            throw new RobotNotReadyException();
    }
    private void checkReady(Future future, Class clazz) {
        checkReady();
        if(future != null || !future.isDone()) {
            Constructor<?> ctor;
            try {
                ctor = clazz.getConstructor(clazz);
                Object object = ctor.newInstance(new Object[] {});
                throw (RuntimeException) object;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Log.e(TAG, "error :", e);
            }
        }
    }

    private void checkFuture(Future future){
        try {
            future.get();
        } catch (ExecutionException e) {
            throw new RuntimeExecutionException(e);
        }
    }


    public void connect(Activity activity) throws RuntimeExecutionException {
        connect(activity, null);
    }

    public void connect(Activity activity, OnFocusLostListener lostListener) throws RuntimeExecutionException {
        checkThread();
        connectionPromise = new Promise<>();
        QiSDK.register(activity, new RobotLifecycleCallbacks() {

            @Override
            public void onRobotFocusGained(QiContext qiContext) {
                Robot.this.qiContext = qiContext;
                connectionPromise.setValue(null);
            }

            @Override
            public void onRobotFocusLost() {
                Robot.this.qiContext = null;
                if(lostListener != null)
                    lostListener.run();
            }

            @Override
            public void onRobotFocusRefused(String reason) {
                connectionPromise.setError(reason);
            }
        });
        try {
            connectionPromise.getFuture().get();
        } catch (ExecutionException e) {
            throw new RuntimeExecutionException(e);
        }
    }

    public void stop() {
        cancelFuture(sayFuture);
        cancelFuture(animateFuture);
        cancelFuture(listenFuture);
        cancelFuture(gotoFuture);
    }

    public void shutUp() {
        checkReady();
        cancelFuture(sayFuture);
    }

    private void cancelFuture(Future future) {
        if(future != null) {
            future.requestCancellation();
        }
    }


    public void say(String phrase) {
        checkReady(sayFuture, RobotAlreadySpeakingException.class);
        sayFuture = SayBuilder.with(qiContext).withText(phrase).build().async().run();
        checkFuture(sayFuture);
    }

    public void say(@StringRes Integer phrase) {
        say(qiContext.getString(phrase));
    }

    public void say(Speech speech) {
        say(speech.getData());
    }

    public QiAnimation createAnimation(int res) {
        checkReady();
        Animation animation = AnimationBuilder.with(qiContext).withResources(res).build();
        QiAnimation qiAnimation = new QiAnimation(res);
        qiAnimation.setDuration(animation.duration());
        qiAnimation.setLabels(animation.labels());
        qiAnimation.setRobotAnimation(animation);

        return qiAnimation;
    }

    public void animate(@RawRes Integer animationRes) {
        QiAnimation qiAnim = createAnimation(animationRes);
        animate(qiAnim);
    }

    public void animate(QiAnimation qiAnimation) {
        checkReady(animateFuture, RobotAlreadyAnimatingException.class);
        animateFuture = AnimateBuilder.with(qiContext)
                .withAnimation(qiAnimation
                .getRobotAnimation())
                .build().async().run();
        checkFuture(animateFuture);
    }

    public void goTo(Location location) {
        checkReady(gotoFuture, RobotAlreadyMovingException.class);
        gotoFuture = GoToBuilder.with(qiContext)
                .withFrame(location.getFrame())
                .build().async().run();
        checkFuture(gotoFuture);
    }

    public String listen(List<String> phrases) {
        checkReady(listenFuture, RobotAlreadyListeningException.class);
        List<PhraseSet> phraseSets = new ArrayList<>();
        for (String phrase : phrases) {
            PhraseSet phraseSet = PhraseSetBuilder.with(qiContext).withTexts(phrase).build();
            phraseSets.add(phraseSet);
        }
        listenFuture = ListenBuilder.with(qiContext)
                .withPhraseSets(phraseSets)
                .build().async().run();

        try {
            return listenFuture.get().getHeardPhrase().getText();
        } catch (ExecutionException e) {
            throw new RuntimeExecutionException(e);
        }
    }

    public String listen(String ...phrases) {
        return listen(Arrays.asList(phrases));
    }

    public Integer listen(Integer ...resources) {
        List<String> strings = new ArrayList<>();
        for (Integer resource : resources) {
            String string = qiContext.getString(resource);
            strings.add(string);
        }
        String result = listen(strings);
        for (Integer resource : resources) {
            String string = qiContext.getString(resource);
            if(string.equals(result))
                return resource;
        }
        return null;
    }


    public void engage(Human human) {
        checkReady();
    }

    public void setOnHumanAround(HumanAroundListener humanAroundListener) {

    }

    public String discuss(@RawRes Integer topic) {
        return null;
    }

    public Speech createSpeech(String sentence) {
        return null;
    }



    public Location createLocation(int x, int y, int z) {
        return new Location();
    }


    public Discussion createDiscussion(int random_dicussion) {
        return null;
    }

    public void discuss(Discussion discussion) {
    }

    public Human createHuman(String firstname, String lastname) {
        return null;
    }

    public void deactivate(Integer hability) {

    }

    public void activate(Integer hability) {

    }

    public void follow(Robot robot) {

    }

    public Human waitForHuman() {

        return null;
    }

    public Location getLocation(String kitchen) {
        return null;
    }

    public void remember(String key, String value) {

    }

    public void rememberLocation(String name, Location location) {

    }


}
