package br.com.frlnrl.brickballplus.engine;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Fabio on 26/11/2017.
 */

public class Controles implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private final GestureDetector gestureDetector;
    private int eventAction = 12345;
    private int angle = 0;
    private boolean onfling;
    private boolean onScroll;
    private Game game;

    public Controles(Context context) {
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent firstMotionEvent, MotionEvent lastMotionEvent, float v, float v1) {
        boolean bottomToTop = false;
        onScroll = true;
        game.setStartLine(true);

        float firstMotionEventX = firstMotionEvent.getX();
        float firstMotionEventY = firstMotionEvent.getY();
        float lastMotionEventX = lastMotionEvent.getX();
        float lastMotionEventY = lastMotionEvent.getY();

        if (firstMotionEventY - lastMotionEventY > 0) {
            bottomToTop = true;
        }

        if (!bottomToTop) {
            game.setAngle((int) getAngle(firstMotionEventX, firstMotionEventY, lastMotionEventX, lastMotionEventY));
            return false;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent firstMotionEvent, MotionEvent lastMotionEvent, float velocityX, float v1) {
        onfling = true;
        float firstMotionEventX = firstMotionEvent.getX();
        float firstMotionEventY = firstMotionEvent.getY();
        float lastMotionEventX = lastMotionEvent.getX();
        float lastMotionEventY = lastMotionEvent.getY();
        final int SWIPE_MIN_DISTANCE = 80;
        final int SWIPE_THRESHOLD_VELOCITY = 50;
        boolean bottomToTop = false;

        //This will test for up and down movement.
        if (firstMotionEvent.getY() - lastMotionEvent.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            bottomToTop = true;
        }

        if (!bottomToTop){
            game.setAngle((int) getAngle(firstMotionEventX, firstMotionEventY, lastMotionEventX, lastMotionEventY));
            return false;
        }

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //repassa o evento para o detector de gestos:
        gestureDetector.onTouchEvent(motionEvent);

        eventAction = motionEvent.getActionMasked();

        if (eventAction == MotionEvent.ACTION_UP) {
            if (onfling || onScroll) {
                game.startBallsBounce();
                //game.setAngle(0);
                onScroll = false;
                onfling = false;
                game.setStartLine(false);
            }
        }
        return true;
    }

    void setGame(Game game){
        this.game = game;
    }

    private double getAngle(float x1, float y1, float x2, float y2) {
        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return ((rad*180/Math.PI + 180)%360) - 180;
    }

}
