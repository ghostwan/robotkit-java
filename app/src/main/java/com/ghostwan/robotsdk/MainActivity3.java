package com.ghostwan.robotsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.ghostwan.robotsdk.sdk.*;

public class MainActivity3 extends AppCompatActivity {

    private TaskRunner runner = new TaskRunner();

    private Pepper myPepper;
    private Nao nao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPepper = new MyPepper();
        nao = new Nao("nono.local");

        runner.start(() -> {
            myPepper.connect();
            nao.connect();

            myPepper.say("Hello Nao!");
            nao.say("Hello Pepper!");

            Location theKitchen = myPepper.getLocation("kitchen");
            if(theKitchen == null) {
                myPepper.say("Nao dis moi! Sais tu ou se trouve la cuisine ?");
                theKitchen = nao.getLocation("kitchen");
                if(theKitchen != null) {
                    nao.say("Oui je t'envoie ça!");
                    myPepper.rememberLocation("kitchen", theKitchen);
                }
                else {
                    nao.say("Désolé je ne sais pas!");
                    myPepper.say("Je ne peux rien faire, aurevoir!");
                    finish();
                }
            }

            myPepper.say("Suis moi Nao!");
            nao.follow(myPepper);

            myPepper.goTo(theKitchen);

            Task t1 = () -> myPepper.say("Nous voilà dans la cuisine!");
            Task t2 = () -> myPepper.animate(R.raw.exclamation_both_hands_a003);
            Task t3 = () -> nao.animate(R.raw.exclamation_both_hands_a003);
            runner.run(Task.parallel(t1,t2, t3));

            Human human = myPepper.waitForHuman();
            myPepper.engage(human);
            String result = myPepper.discuss(R.raw.cooking_dicussion);
            myPepper.remember("discussion:result", result);
            nao.remember("discussion:result", result);


            //Another way to do it


            Speech speech = myPepper.createSpeech("Hello world");
            speech.setOnStartListener(() -> {

            });
            myPepper.say(speech);

            Location location = myPepper.createLocation(10, 20, 30);
            myPepper.goTo(location);

            Animation animation = myPepper.createAnimation(R.raw.dog_a001);
            animation.getDuration();
            animation.getLabels();
            myPepper.animate(animation);

            Discussion discussion = myPepper.createDiscussion(R.raw.cooking_dicussion);
            myPepper.discuss(discussion);

            myPepper.deactivate(Robot.AUTONOMOUS_BLINKING);
            Human human2 = myPepper.createHuman("Erwan","Pinault");
            myPepper.engage(human);

            myPepper.activate(Robot.AUTONOMOUS_BLINKING);



        });

    }

}
