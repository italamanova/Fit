package com.example.ira.fit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ira.fit.Accel.AccelerometerResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SitUpsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    int permissionCheck;
    final static int MY_PERMISSIONS_REQUEST_VIBRATE = 101;
    final static int MY_PERMISSIONS_REQUEST_STORAGE = 102;
    private int squats = 0;
    public Vibrator v;
    private AccelerometerResult accelerometerResult;
    private static final String log = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_ups);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            makeSquatsPermission();
            senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);//SENSOR_DELAY_NORMAL);
            permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.VIBRATE);
            accelerometerResult = new AccelerometerResult();

            accelerometerResult.setmAccel(0.00f);
            accelerometerResult.setmAccelCurrent(SensorManager.GRAVITY_EARTH);
            accelerometerResult.setmAccelLast(SensorManager.GRAVITY_EARTH);

        } else {
            Log.v("FitLog", "No accelerometer");
        }

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void makeSquatsPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.VIBRATE)) {
                Toast.makeText(this, "No vibration permission", Toast.LENGTH_SHORT).show();

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.VIBRATE},
                        MY_PERMISSIONS_REQUEST_VIBRATE);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.VIBRATE)) {
                Toast.makeText(this, "No storage permission", Toast.LENGTH_SHORT).show();

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_VIBRATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    incrementSquats();

                } else {

                    Toast.makeText(this, "No vibration permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    incrementSquats();

                } else {

                    Toast.makeText(this, "No storage permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void incrementSquats() {
        squats++;
        TextView textElement = (TextView) findViewById(R.id.squats_counter);
        textElement.setText("" + squats);
        v.vibrate(50);
    }

    public void appendLog(String text) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved");
        myDir.mkdirs();
        String fname = "test.txt";

        try {

            FileOutputStream outputStream;
            File file = new File ("/storage/emulated/0/folder/", fname);
            outputStream = new FileOutputStream(file, true);
            outputStream.write(text.getBytes());
            outputStream.close();
            //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //if (accelerometerResult.isAction(sensorEvent)) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();
            String res = "Time: " + curTime + " :: X: " + x + " Y: " + y + " Z: " + z + "\n";
            appendLog(res);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}

