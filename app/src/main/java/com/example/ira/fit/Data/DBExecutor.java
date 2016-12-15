package com.example.ira.fit.Data;

/**
 * Created by Ira on 01.12.2016.
 */

import java.util.ArrayList;
import java.util.List;
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
            if(cursor.moveToFirst()) {
                res = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
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
                        Integer.parseInt(cursor.getString(startTimeColInd)),
                        Integer.parseInt(cursor.getString(endTimeColInd)),
                        Integer.parseInt(cursor.getString(quantityColInd)),
                        getLastQuantity(cursor.getString(typeColInd)),
                        getMaxQuantity(cursor.getString(typeColInd))
                );
                mExerciseList.add(ex);
            } while (cursor.moveToNext());

        } else {
            Log.d(LOG_TAG, "No data in the Database!");
        }

        cursor.close();

        return mExerciseList;

    }

    public void close() {
        dbHelper.close();
        db.close();
    }

}