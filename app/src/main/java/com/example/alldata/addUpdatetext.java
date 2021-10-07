package com.example.alldata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class addUpdatetext extends AppCompatActivity {
    private SQLiteDatabase mydatabase;
    private appdatabase appdatabase;
    private EditText messgebox;
    private texts textsobject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_updatetext);
        messgebox = findViewById(R.id.add_update_message);
        appdatabase = new appdatabase(this);
        mydatabase = appdatabase.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addupdatemenufile,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.samemessage:
                //Toast.makeText(this, "saving....work in progreess", Toast.LENGTH_SHORT).show();
                String content = messgebox.getText().toString();
                Calendar cal = Calendar.getInstance();
                long time = cal.getTimeInMillis();
                ContentValues cv = new ContentValues();
                cv.put(Contractfortables.textstable.COLUMN_TEXT,content);
                cv.put(Contractfortables.textstable.COLUMN_DATE,time);
                mydatabase.insert(Contractfortables.textstable.TABLE_NAME,null,cv);

                textsobject = new texts();
                //textsobject.demo(this);
                //Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}