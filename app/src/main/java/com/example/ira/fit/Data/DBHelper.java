package com.example.ira.fit.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "myLog";

    private static final String DATABASE_NAME = "exerciseDB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_EXERCISE_TABLE_SQL = "create table "
            + ExerciseContract.ExerciseEntry.TABLE_NAME +" ("
            + ExerciseContract.ExerciseEntry._ID + " integer primary key autoincrement,"
            + ExerciseContract.ExerciseEntry.KEY_TYPE + " text,"
            + ExerciseContract.ExerciseEntry.KEY_START_TIME + " text,"
            + ExerciseContract.ExerciseEntry.KEY_END_TIME + " text,"
            + ExerciseContract.ExerciseEntry.KEY_QUANTITY + " integer"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXERCISE_TABLE_SQL);
        Log.v(LOG_TAG, "DATABASE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExerciseContract.ExerciseEntry.TABLE_NAME);
        this.onCreate(db);
    }

}

