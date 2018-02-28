package com.ghostwan.robotsdk.sdk;


import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

/**
 * Created by erwan on 27/02/2018.
 */

public class Robot {
    public static final Integer AUTONOMOUS_BLINKING = 1;
    private String address;

    public Robot(String address) {
        this.address = address;
    }

    public void say(String phrase) {

    }

    public void say(@StringRes Integer phrase) {

    }

    public void animate(String animation) {

    }

    public void animate(@RawRes Integer animation) {

    }

    public void connect() {

    }

    public void shutUp() {

    }

    public void stopAnimation() {

    }

    public void goTo(Location theKitchen) {

    }

    public void stopMove() {

    }

    public void stopMoving() {
        
    }

    public String listen(String ...phrases) {

        return null;
    }

    public Integer listen(Integer ...ressources) {

        return null;
    }

    public void stop() {

    }

    public void stopSpeaking() {

    }

    public void stopGoing() {
    }

    public void doTask(Object say, String s) {

    }

    public void engage(Human human) {

    }

    public void setOnHumanAround(HumanAroundListener humanAroundListener) {

    }

    public String discuss(@RawRes Integer topic) {
        return null;
    }

    public Speech createSpeech(String sentence) {
        return null;
    }

    public void say(Speech speech) {

    }

    public Location createLocation(int x, int y, int z) {
        return new Location();
    }

    public Animation createAnimation(int res) {
        return null;
    }

    public void animate(Animation animation) {

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
