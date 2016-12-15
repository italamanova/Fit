package com.example.ira.fit.Data;

import android.provider.BaseColumns;

/**
 * Created by Ira on 01.12.2016.
 */

public class ExerciseContract  {

    public static final class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String KEY_TYPE = "type";
        public static final String KEY_START_TIME = "time_start";
        public static final String KEY_END_TIME = "time_end";
        public static final String KEY_QUANTITY = "quantity";
    }
}