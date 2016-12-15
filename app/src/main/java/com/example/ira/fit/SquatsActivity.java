package com.example.ira.fit;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ira.fit.Model.Exercise;

import java.util.Calendar;


public class SquatsActivity extends AppCompatActivity {
    private static final String log = "logSquatsActivity";
    Exercise exercise;
    int exQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats);
        TextView getLastResult = (TextView) findViewById(R.id.last_res_text_view);
        exQuantity = 0;
        //this.deleteDatabase("exerciseDB");
        exercise = new Exercise(this,Exercise.SQUAT);
        getLastResult.setText("" + exercise.getMaxQuantity());
        final Button addSquatButton = (Button) findViewById(R.id.button_add_squat);
        addSquatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSquat();
            }
        });

        final Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endSession();
                startActivity(new Intent(SquatsActivity.this, MainActivity.class));
            }
        });
    }

    private void addSquat(){
        exQuantity++;
        TextView textElement = (TextView) findViewById(R.id.squats_counter);
        textElement.setText("" + exQuantity);
    }

    private void endSession(){
        Calendar c = Calendar.getInstance();
        exercise.setEndTime(c.get(Calendar.SECOND));
        exercise.setQuantity(exQuantity);
        exercise.logEx();
        exercise.insert();
    }
}
