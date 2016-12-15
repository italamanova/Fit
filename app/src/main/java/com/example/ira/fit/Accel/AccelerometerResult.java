package com.example.ira.fit.Accel;

import android.hardware.SensorEvent;

/**
 * Created by Ira on 06.11.2016.
 */

public class AccelerometerResult {
    private static final String log = "myLog";
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private long lastUpdate = 0;
    private int cnt;


    public AccelerometerResult() {
        clear();
    }

    private void clear() {
        cnt = 0;

    }

    public boolean isAction(SensorEvent sensorEvent) {
        boolean isSquat = false;
        mGravity = sensorEvent.values.clone();

        LinearAcceleration linearAcceleration = new LinearAcceleration(mGravity[0],mGravity[1],mGravity[2]);   // Shake detection
        
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 200) {
            //long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            linearAcceleration.uppdateValues();
            mAccelLast = mAccelCurrent;
            mAccelCurrent = linearAcceleration.getCurrentAcceleration();
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            //Log.v(log, "mAccel: " + mAccel + " mAccelLast: " + mAccelLast + " mAccelCurrent: " + mAccelCurrent);
            if (mAccel > 3 && linearAcceleration.checkLie()) {
                isSquat = true;
            }
        }
        return isSquat;
    }

    public void setmAccel(float mAccel) {
        this.mAccel = mAccel;
    }

    public void setmAccelCurrent(float mAccelCurrent) {
        this.mAccelCurrent = mAccelCurrent;
    }

    public void setmAccelLast(float mAccelLast) {
        this.mAccelLast = mAccelLast;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public float getmAccel() {
        return mAccel;
    }

    public float getmAccelCurrent() {
        return mAccelCurrent;
    }

    public float getmAccelLast() {
        return mAccelLast;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public float[] getmGravity() {
        return mGravity;
    }

    public void setmGravity(float[] mGravity) {
        this.mGravity = mGravity;
    }
}
