package com.example.alldata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class appdatabase extends SQLiteOpenHelper {
    private static final String CREATE_TABLE =
            "CREATE TABLE " + Contractfortables.textstable.TABLE_NAME + " (" +
                    Contractfortables.textstable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT  ," +
                    Contractfortables.textstable.COLUMN_DATE + " int ," +
                    Contractfortables.textstable.COLUMN_TEXT + " TEXT)";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + Contractfortables.textstable.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Messages.db";

    public appdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
