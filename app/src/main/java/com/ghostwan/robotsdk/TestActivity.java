package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.aldebaran.qi.Promise;
import com.ghostwan.robotsdk.sdk.*;

public class TestActivity extends AppCompatActivity {

    private Pepper myPepper;
    private Location theKitchen = new Location();
    private TaskRunner runner = new TaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        myPepper = new MyPepper();
        runner.start( () -> myPepper.connect(TestActivity.this));

    }

    public void onSayHello(View view) {
        Task task = () -> {
            myPepper.say("Hello world");
            myPepper.animate(R.raw.dog_a001);
            myPepper.goTo(theKitchen);
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
