package com.example.ira.fit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ira.fit.Model.Exercise;
import com.example.ira.fit.Motion.AccelerometerResult;
import com.example.ira.fit.Motion.MotionDetector;


public class ExerciseActivity extends AppCompatActivity implements SensorEventListener {
    private static final String log = "logSquatsActivity";
    Exercise exercise;
    int exQuantity;
    private String exerciseType;
    
    private SensorManager mSensorManager;
    private Sensor senAccelerometer;
    private Sensor mProximity;

    AccelerometerResult accelerometerResultSquats;
    AccelerometerResult accelerometerResultPullUps;
    AccelerometerResult accelerometerResultSitUps;
    
    public Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        
        exQuantity = 0;
        exerciseType = getIntent().getStringExtra("EXTRA_EXERCISE_TYPE");
        exercise = new Exercise(this,exerciseType);

        this.setTitle(exerciseType);
        TextView exerciseName = (TextView) findViewById(R.id.exercise_name_text_view);
        exerciseName.setText(exerciseType);
        
        TextView getLastResult = (TextView) findViewById(R.id.best_res_text_view);
        getLastResult.setText("" + exercise.getMaxQuantity());

        if(exerciseType.equals(Exercise.PUSHUP)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        final Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                exercise.endSession(exQuantity, exQuantity);
                startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        
        accelerometerResultSquats = new AccelerometerResult(MotionDetector.xyzMIN_MAX, 2, 0.75f);
        accelerometerResultPullUps = new AccelerometerResult(MotionDetector.xyzMAX_MIN, 4, 0.75f);
        accelerometerResultSitUps = new AccelerometerResult(MotionDetector.xyzMAX_MIN, 2, 0.85f);

        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void addExercise(){
        exQuantity++;
        TextView textElement = (TextView) findViewById(R.id.squats_counter);
        textElement.setText("" + exQuantity);
        v.vibrate(50);
    }
    
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(exerciseType.equals(Exercise.PUSHUP)) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (sensorEvent.values[0] >= -0.01 && sensorEvent.values[0] <= 0.01) {
                    Log.v(log, "PUSHUP");
                    //near
                    addExercise();
                    //Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                } else {
                    //far
                    //.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (exerciseType.equals(Exercise.SQUAT)) {
                    if (accelerometerResultSquats.isAction(sensorEvent)) {
                        Log.v(log, "SQUAT");
                        addExercise();
                    }
                }
                if (exerciseType.equals(Exercise.PULLUP)) {
                    if (accelerometerResultPullUps.isAction(sensorEvent)) {
                        Log.v(log, "PULLUP");
                        addExercise();
                    }
                }
                if (exerciseType.equals(Exercise.SITUP)) {
                    if (accelerometerResultSitUps.isAction(sensorEvent)) {
                        Log.v(log, "SITUP");
                        addExercise();
                    }
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
