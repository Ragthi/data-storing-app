package com.example.alldata;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class texts extends Fragment {
    private RecyclerView recyclerView;
    private textsrecycleradapter adapter;
    private SQLiteDatabase mydatabase;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.texts,container,false);

        appdatabase appdatabase = new appdatabase(getContext());
        mydatabase = appdatabase.getWritableDatabase();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.text_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new textsrecycleradapter(getContext(),getalldata());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.actionbarbutton, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addtext:
                Intent movetoaddupdate = new Intent(getContext(),addUpdatetext.class);
                startActivity(movetoaddupdate);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Cursor getalldata(){
        return mydatabase.query(
                Contractfortables.textstable.TABLE_NAME,
                null,
                null
                ,null
                ,null
                ,null
                ,Contractfortables.textstable._ID + " DESC"
        );
    }

}
