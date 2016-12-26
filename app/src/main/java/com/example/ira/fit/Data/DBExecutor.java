package com.example.ira.fit.Data;

/**
 * Created by Ira on 01.12.2016.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.ira.fit.Model.Exercise;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBExecutor {

    private static final String LOG_TAG = "my_tag";
    DBHelper dbHelper;
    Context context;
    Cursor cursor;
    SQLiteDatabase db;
    List<Exercise> mExerciseList;

    public DBExecutor(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public int getItemCount() {

        db = dbHelper.getReadableDatabase();

        cursor = db.query(ExerciseContract.ExerciseEntry.TABLE_NAME, null, null, null, null, null, null);
        int cnt = cursor.getCount();
        cursor.close();

        return cnt;
    }

    public void insertExercise(Exercise ex){
        ContentValues cv = new ContentValues();
        cv.put(ExerciseContract.ExerciseEntry.KEY_TYPE, ex.getType());
        cv.put(ExerciseContract.ExerciseEntry.KEY_START_TIME, ex.getStartTime());
        cv.put(ExerciseContract.ExerciseEntry.KEY_END_TIME, ex.getEndTime());
        cv.put(ExerciseContract.ExerciseEntry.KEY_QUANTITY, ex.getQuantity());
        db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, cv);
    }

    public void updateQuantity(String name, String newQuant){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ExerciseContract.ExerciseEntry.KEY_QUANTITY, newQuant);
        String[] args = new String[]{name};
        db.update(ExerciseContract.ExerciseEntry.TABLE_NAME, cv, "name = ?", args);
    }

    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(ExerciseContract.ExerciseEntry.TABLE_NAME, ExerciseContract.ExerciseEntry._ID + "=" + id, null);
    }

    public int getLastQuantity(String type){
        int res = 0;
        if(this.getItemCount() != 0) {
            db = dbHelper.getWritableDatabase();
            String selectQuery= "SELECT * FROM " + ExerciseContract.ExerciseEntry.TABLE_NAME+" WHERE " +  ExerciseContract.ExerciseEntry.KEY_TYPE + "='" + type +"'" +" ORDER BY "+ExerciseContract.ExerciseEntry.KEY_QUANTITY+" DESC LIMIT 1";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst())
                res  =  Integer.parseInt(cursor.getString( cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_QUANTITY)));
            cursor.close();
        }
        return res;
    }

    public int getMaxQuantity(String type){
        int res = 0;
        if(this.getItemCount() != 0) {
            db = dbHelper.getWritableDatabase();
            String selectQuery= "SELECT MAX(" +  ExerciseContract.ExerciseEntry.KEY_QUANTITY + ") FROM " +  ExerciseContract.ExerciseEntry.TABLE_NAME + " WHERE " +  ExerciseContract.ExerciseEntry.KEY_TYPE + "='" + type + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                        res = Integer.parseInt(cursor.getString(0));
                }
            }catch(Exception e){
                Log.e("getMaxQuantity", "Empty cursor");
            }
            finally {
                cursor.close();
            }
        }
        return res;
    }

    public List<Exercise> getAllExercises() {
        cursor = db.query(ExerciseContract.ExerciseEntry.TABLE_NAME, null, null, null, null, null, null);
        mExerciseList = new ArrayList<Exercise>();

        if (cursor.moveToFirst()) {
            int typeColInd = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_TYPE);
            int startTimeColInd = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_START_TIME);
            int endTimeColInd = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_END_TIME);
            int quantityColInd = cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_QUANTITY);

            do {
                Exercise ex = new Exercise(
                        cursor.getString(typeColInd),
                        cursor.getString(startTimeColInd),
                        cursor.getString(endTimeColInd),
                        Integer.parseInt(cursor.getString(quantityColInd))
                );
                mExerciseList.add(ex);
            } while (cursor.moveToNext());

        } else {
            Log.d(LOG_TAG, "No data in the Database!");
        }

        cursor.close();

        return mExerciseList;

    }

    public String getSumTime(String type){
        //String timeMills = "";
        long timeMills = 0;

        if(this.getItemCount() != 0) {
            db = dbHelper.getWritableDatabase();
            String startQuery= "SELECT " + ExerciseContract.ExerciseEntry.KEY_END_TIME +","+  ExerciseContract.ExerciseEntry.KEY_START_TIME +
                    " FROM " + ExerciseContract.ExerciseEntry.TABLE_NAME +
                    " WHERE " +  ExerciseContract.ExerciseEntry.KEY_TYPE + "='" + type + "'";
            Log.v("myLog",startQuery);
            Cursor cursor = db.rawQuery(startQuery, null);
            try {
                while (cursor.moveToNext()) {
                    timeMills += (convertToMiliseconds(cursor.getString(cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_END_TIME)))
                            -(convertToMiliseconds(cursor.getString(cursor.getColumnIndex(ExerciseContract.ExerciseEntry.KEY_START_TIME)))));
                }
            } finally {
                cursor.close();
            }
        }
        return convertTime(timeMills);
    }

    private String convertTime(long millis){
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public int getSumExercises(String type){
        int sumEx = 0;
        if(this.getItemCount() != 0) {
            db = dbHelper.getWritableDatabase();
            String startQuery= "SELECT SUM(" + ExerciseContract.ExerciseEntry.KEY_QUANTITY + ") FROM " +  ExerciseContract.ExerciseEntry.TABLE_NAME + " WHERE " +  ExerciseContract.ExerciseEntry.KEY_TYPE + "='" + type + "'";
            Cursor cursor = db.rawQuery(startQuery, null);
            if(cursor.moveToFirst()) {
                sumEx = Integer.parseInt(cursor.getString(0));
            }
        }
        return sumEx;
    }

    public long convertToMiliseconds(String input){
        long millisecondsFromNow = 0;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(input);
            long milliseconds = date.getTime();
            millisecondsFromNow = milliseconds - (new Date()).getTime();
        }catch(Exception e){

        }
        return millisecondsFromNow;
    }

    public String getDateTime(long timeInMills) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(timeInMills);
        return dateFormat.format(date);
    }

    public ArrayList<Float> get7DaysrResults(String type){
        Float[] array = {0f,0f,0f,0f,0f,0f,0f};
        ArrayList<Float> arrayList = null;
        if(this.getItemCount() != 0) {
            db = dbHelper.getWritableDatabase();
            String startQuery= "SELECT  date(" + ExerciseContract.ExerciseEntry.KEY_END_TIME + ")," +
                    " SUM(" + ExerciseContract.ExerciseEntry.KEY_QUANTITY + "),"+
                    " strftime('%w', " + ExerciseContract.ExerciseEntry.KEY_END_TIME +") "+
                    " FROM " +  ExerciseContract.ExerciseEntry.TABLE_NAME +
                    " WHERE " +  ExerciseContract.ExerciseEntry.KEY_TYPE + "='" + type + "'" +
                    " AND " + ExerciseContract.ExerciseEntry.KEY_END_TIME +
                    " BETWEEN datetime('now', '-7 days') AND datetime('now', 'localtime') " +
                    "GROUP BY date(" + ExerciseContract.ExerciseEntry.KEY_END_TIME + ");";
            Log.v("sql",startQuery);
            Cursor cursor = db.rawQuery(startQuery, null);

            try {
                while (cursor.moveToNext()) {
                    int index = cursor.getInt(cursor.getColumnIndex("strftime('%w', " + ExerciseContract.ExerciseEntry.KEY_END_TIME +")"));
                    array[index] = Float.parseFloat(cursor.getString(cursor.getColumnIndex("SUM(" + ExerciseContract.ExerciseEntry.KEY_QUANTITY + ")")));
                }

            }catch(Exception e){
                Log.e("error",e.getLocalizedMessage());
            }
            finally {
                cursor.close();
            }
            arrayList = new ArrayList<Float>(Arrays.asList(array));
            for(int i = 0; i < arrayList.size();i++){
                if(arrayList.get(i) != null) {
                    Log.v("array", "" + arrayList.get(i));
                }else{
                    Log.v("array", "null");
                }
            }
        }
        return arrayList;


    }
    public void close() {
        dbHelper.close();
        db.close();
    }

}