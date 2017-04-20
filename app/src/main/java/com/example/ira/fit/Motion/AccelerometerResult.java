package com.example.ira.fit.Motion;

import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by Ira on 06.11.2016.
 */

public class AccelerometerResult {
    private static String log = "myLog";
    float filterCoef;


    SimpleLinearFilter simpleLinearFilter;
    MotionDetector motionDetector;

    public AccelerometerResult(int type, int epsylon, float filterCoef, String fileName, String fnameExtr) {
        simpleLinearFilter = new SimpleLinearFilter();
        motionDetector = new MotionDetector(type, epsylon);
        motionDetector.fname = fileName;
        motionDetector.fnameExtr = fnameExtr;
        this.filterCoef = filterCoef;
           }

    public boolean isAction(SensorEvent sensorEvent) {
        float[] original = sensorEvent.values;
        Log.v(log,"original: " + original[0] + " " + original[1] + " " + original[2]);
        simpleLinearFilter.setFilterCoefficient(filterCoef);
        float[] filtered = simpleLinearFilter.filter(original);
        Log.v(log,"filtered: " + filtered[0] + " " + filtered[1] + " " + filtered[2]);
        motionDetector.add(original,filtered);
        return motionDetector.isShake();
    }


}
