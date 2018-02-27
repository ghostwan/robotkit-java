package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.ghostwan.robotsdk.sdk.*;

public class MainActivity extends AppCompatActivity {

    private Pepper myPepper;
    private Location theKitchen = new Location();
    private TaskRunner runner = new TaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPepper = new MyPepper();
        runner.run( () -> myPepper.connect());

    }

    public void onSayHello(View view) {
        Task task = () -> {
            myPepper.say("Hello world");
            myPepper.animate(R.raw.dog_a001);
            myPepper.goTo(theKitchen);
        };
        runner.run(task);
    }

    public void onStopEverything(View view) {
        runner.run( () -> myPepper.stop());
    }

    public void onStopSpeaking(View view) {
        runner.run( () -> myPepper.stopSpeaking());
    }

    public void onStopMoving(View view) {
        runner.run( () -> myPepper.stopMoving());
    }

    public void onStopGoing(View view) {
        runner.run( () -> myPepper.stopGoing());
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
        runner.run(task);
    }
}
