package com.example.alldata;

import android.provider.BaseColumns;

public final class Contractfortables {

    private Contractfortables(){

    }
    public static class textstable implements BaseColumns{
        public static final String TABLE_NAME = "Messages";
        public static final String COLUMN_TEXT = "entry";
        public static final String COLUMN_DATE = "Date";
    }
}
