package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.ghostwan.robotsdk.sdk.*;
import com.ghostwan.robotsdk.sdk.exception.RuntimeExecutionException;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    private Pepper myPepper;
    private TaskRunner runner = new TaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        myPepper = new MyPepper();
        runner.start(() -> {
            try {
                myPepper.connect(TestActivity.this);
                runOnUiThread(() -> findViewById(R.id.layout).setVisibility(View.VISIBLE));
            } catch (RuntimeExecutionException e) {
                Log.e(TAG, "error :", e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        myPepper.disconnect(this);
        super.onDestroy();
    }

    public void onSayHello(View view) {
        Task task = () -> {
            myPepper.say("Hello world");
            myPepper.animate(R.raw.dog_a001);
            myPepper.animate(R.raw.dog_a001);
            myPepper.animate(R.raw.dog_a001);
        };
        runner.start(task);
    }

    public void onStopEverything(View view) {
        runner.run( () -> myPepper.stop());
    }

    public void onListenAndTalk(View view) {
        Task task = () -> {
            Integer result = myPepper.listen(R.string.hello_pepper, R.string.how_are_you, R.string.what_are_you_doing);
            switch (result){
                case R.string.hello_pepper : myPepper.say("Hello human"); break;
                case R.string.how_are_you : myPepper.say("I find and you ?"); break;
                case R.string.what_are_you_doing : myPepper.say("Nothing what about you ?"); break;
            }
        };
        runner.start(task);
    }


    public void speakAndMove(@StringRes Integer phrase, @RawRes Integer animation) {
        Task sayTask = () -> myPepper.say(phrase);
        Task animateTask = () -> myPepper.animate(animation);

        Task taskParallel = Tasks.parallel(sayTask, animateTask);
//        Task taskIterative = Task.iterative(sayTask, animateTask);

        runner.start(taskParallel);
    }

    public void onSpeakAndMove(View view) {
        speakAndMove(R.string.hello_pepper, R.raw.exclamation_both_hands_a003);
    }
}
