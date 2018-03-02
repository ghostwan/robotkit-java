package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.ghostwan.robotsdk.sdk.*;

public class ScenarioActivity extends AppCompatActivity {

    private TaskRunner runner = new TaskRunner();

    private Pepper myPepper;
//    private Nao nao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        myPepper = new MyPepper();
//        nao = new Nao("nono.local");

        runner.start(() -> {
            myPepper.connect(ScenarioActivity.this);
//            nao.connect(ScenarioActivity.this);

            myPepper.say("Hello Nao!");
//            nao.say("Hello Pepper!");

            Location theKitchen = myPepper.getLocation("kitchen");
            if(theKitchen == null) {
                myPepper.say("Nao dis moi! Sais tu ou se trouve la cuisine ?");
//                theKitchen = nao.getLocation("kitchen");
//                if(theKitchen != null) {
//                    nao.say("Oui je t'envoie ça!");
//                    myPepper.rememberLocation("kitchen", theKitchen);
//                }
//                else {
//                    nao.say("Désolé je ne sais pas!");
//                    myPepper.say("Je ne peux rien faire, au revoir!");
//                    finish();
//                }
            }

            myPepper.say("Suis moi Nao!");
//            nao.follow(myPepper);

//            myPepper.goTo(theKitchen);

            Task t1 = () -> myPepper.say("Nous voilà dans la cuisine!");
            Task t2 = () -> myPepper.animate(R.raw.exclamation_both_hands_a003);
//            Task t3 = () -> nao.animate(R.raw.exclamation_both_hands_a003);

            Tasks.parallel(t1, t2).execute();

            Human human = myPepper.waitForHuman();
            myPepper.engage(human);
            String result = myPepper.discuss(R.raw.cooking_dicussion);
            myPepper.remember("discussion:result", result);
//            nao.remember("discussion:result", result);


            //Another way to do it


//            Speech speech = myPepper.createSpeech("Hello world");
//            speech.setOnStartListener(() -> {

//            });
//            myPepper.say(speech);

//            Location location = myPepper.createLocation(10, 20, 30);
//            myPepper.goTo(location);
//
//            QiAnimation animation = myPepper.createAnimation(R.raw.dog_a001);
//            animation.getDuration();
//            animation.getLabels();
//            myPepper.animate(animation);
//
//            Discussion discussion = myPepper.createDiscussion(R.raw.cooking_dicussion);
//            myPepper.discuss(discussion);
//
//            myPepper.deactivate(Robot.AUTONOMOUS_BLINKING);
//            Human human2 = myPepper.createHuman("Erwan","Pinault");
//            myPepper.engage(human);
//
//            myPepper.activate(Robot.AUTONOMOUS_BLINKING);



        });

    }

}
