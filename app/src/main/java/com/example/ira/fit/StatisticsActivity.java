package com.example.ira.fit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ira.fit.Data.DBExecutor;
import com.example.ira.fit.Model.Exercise;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class StatisticsActivity extends AppCompatActivity {
    
    DBExecutor dbExecutor = new DBExecutor(this);
    String exerciseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //!!!!!!!!!!!!!!!!!!!!!!
        exerciseType = Exercise.SQUAT;//getIntent().getStringExtra("EXTRA_EXERCISE_TYPE_FOR_STATISTICS");
        
        String timeExercises = dbExecutor.getSumTime(exerciseType);
        String quantityExercises = "" + dbExecutor.getSumExercises(exerciseType);

        TextView duration = (TextView) findViewById(R.id.duration_squats_text_view);
        duration.setText(timeExercises);

        TextView quantity = (TextView) findViewById(R.id.quantity_squats_text_view);
        quantity.setText(quantityExercises);

        BarChart barChart = (BarChart)findViewById(R.id.chart);
        //setContentView(barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Float> exdata = dbExecutor.get7DaysrResults(exerciseType);
        for(int i = 0;i < exdata.size();i++){
            entries.add(new BarEntry(exdata.get(i), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");
        labels.add("Sunday");

        BarData data = new BarData(labels, dataset);
        barChart.setDescription("# Squats");
        barChart.setData(data);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(1000);
    }
    private BarChart createBarChart(BarChart chart) {
        chart.enableScroll();
        chart.setDoubleTapToZoomEnabled(true);
        chart.setDrawGridBackground(true);
        chart.setDrawBarShadow(false);
        chart.setPinchZoom(false);
        chart.setScaleYEnabled(false);
        chart.setScaleXEnabled(false);
        chart.setDrawValueAboveBar(false);

        return chart;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
