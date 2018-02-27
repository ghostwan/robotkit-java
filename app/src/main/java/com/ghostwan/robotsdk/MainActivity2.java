package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.ghostwan.robotsdk.sdk.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity2 extends AppCompatActivity {

    private Pepper myPepper;
    private Location theKitchen = new Location();
    private ExecutorService executor = Executors.newWorkStealingPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPepper = new MyPepper();
        executor.submit(() -> myPepper.connect());
    }

    public void onSayHello(View view) {
        Task task = () -> {
            myPepper.say("Hello world");
            myPepper.animate(R.raw.dog_a001);
            myPepper.goTo(theKitchen);
        };
        executor.submit(task);
    }

    public void onStopEverything(View view) {
        executor.submit( () -> myPepper.stop());
    }

    public void onStopSpeaking(View view) {
        executor.submit(() -> myPepper.stopSpeaking());
    }

    public void onStopMoving(View view) {
        executor.submit( () -> myPepper.stopMoving());
    }

    public void onStopGoing(View view) {
        executor.submit(() -> myPepper.stopGoing());
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
        executor.submit(task);
    }


    public void speakAndMove(@StringRes Integer phrase, @RawRes Integer animation) {
        Task sayTask = () -> myPepper.say(phrase);
        Task animateTask = () -> myPepper.animate(animation);

        Task taskParallel = Task.parallel(sayTask, animateTask);
        Task taskIterative = Task.iterative(sayTask, animateTask);

        executor.submit(taskParallel);
    }
}
