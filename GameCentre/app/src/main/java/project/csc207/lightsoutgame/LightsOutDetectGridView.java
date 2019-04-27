package project.csc207.lightsoutgame;

/*
Adapted from GestureDetectGridView and therefore:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

public class LightsOutDetectGridView extends GridView {
    /**
     * Minimum swipe distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * Gesture detector
     */
    private GestureDetector gDetector;
    /**
     * Switch controller
     */
    private SwitchController sController;
    /**
     * Check for fling motion
     */
    private boolean mFlingConfirmed = false;
    /**
     * Touch event coordinates
     */
    private float mTouchX;
    private float mTouchY;


    /**
     * Lights out gesture detect grid view.
     *
     * @param context the context
     */
    public LightsOutDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Lights out gesture detect grid view.
     *
     * @param context the context
     * @param attrs   attributes
     */
    public LightsOutDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Lights out gesture detect grid view.
     *
     * @param context      the context
     * @param attrs        attributes
     * @param defStyleAttr style attributes
     */
    public LightsOutDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialize lights out gesture detect grid view
     *
     * @param context the context
     */
    private void init(final Context context) {
        sController = new SwitchController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = LightsOutDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                sController.processTapSwitch(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }


    /**
     * Check if touch event is intercepted and onTouchEvent should be called
     *
     * @param ev motion event
     * @return is touch event intercepted
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Detect touch event. Suggested override of performClick is not needed and was not in the
     * original.
     *
     * @param ev motion event
     * @return the gesture detector consumed the event
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Set lights out board manager for the switch controller
     *
     * @param lightsOutBoardManager lights out board manager
     */
    public void setLightsOutBoardManager(LightsOutBoardManager lightsOutBoardManager) {
        sController.setLightsOutBoardManager(lightsOutBoardManager);
    }
}
