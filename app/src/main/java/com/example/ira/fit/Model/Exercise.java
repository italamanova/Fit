package com.example.ira.fit.Model;

import android.content.Context;
import android.util.Log;

import com.example.ira.fit.Data.DBExecutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Ira on 01.12.2016.
 */

public class Exercise {
    public static final String LOG = "myLog EXERSISE";

    public static final String SQUAT = "Squats";
    public static final String SITUP = "Sit-ups";
    public static final String PUSHUP = "Push-ups";
    public static final String PULLUP = "Pull-ups";

    private String type;
    private String startTime;
    private String endTime;
    private int quantity;
    private int lastQuantity;
    private int maxQuantity;
    private DBExecutor dbExec;
    public Context context;

    public Exercise(Context context,String type) {
        this.context = context;
        dbExec = new DBExecutor(context);
        this.type = type;
        this.startTime = dbExec.getDateTime(System.currentTimeMillis());
        this.endTime = "";
        this.quantity = -1;
        this.lastQuantity = dbExec.getLastQuantity(type);
        this.maxQuantity = dbExec.getMaxQuantity(type);
    }

    public Exercise(String type, String startTime, String endTime, int quantity) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantity = quantity;
        //this.lastQuantity = dbExec.getLastQuantity(type);
        //this.maxQuantity = dbExec.getMaxQuantity(type);

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return  startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLastQuantity() {
        return lastQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void insert(){
        dbExec.insertExercise(this);
    }

    public void close(){
        dbExec.close();
    }


    @Override
    public String toString() {
        return "Exercise{" +
                "type='" + type + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", quantity=" + quantity +
                ", lastQuantity=" + lastQuantity +
                ", dbExec=" + dbExec +
                ", context=" + context +
                '}';
    }

    public void endSession(int quantity, int i){
        this.setEndTime(dbExec.getDateTime(System.currentTimeMillis()));
        this.setQuantity(quantity);
        this.logEx();
        this.insert();
    }

    public void logEx(){
        Log.v(LOG,"COUNT: " + dbExec.getItemCount());
        Log.v(LOG,this.toString());
    }
}
