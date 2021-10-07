package com.example.alldata;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class photosfragment extends Fragment {
    private photosrecycleradapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView =  inflater.inflate(R.layout.photos,container,false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.photosrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new photosrecycleradapter(getallImages(),getActivity());
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
                Toast.makeText(getContext(), "work in progress", Toast.LENGTH_SHORT).show();
                //add extra Bitmap or somthing
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<String> getallImages(){
        List<String> imageBitmap=new ArrayList<String>();
        ContextWrapper cw = new ContextWrapper(getActivity().getBaseContext());
        File directory = cw.getDir("MSJR_Photos", Context.MODE_PRIVATE);
        File[] filelist = directory.listFiles();
        for (File f : filelist)
        {
            imageBitmap.add(f.getAbsolutePath());
        }
        return imageBitmap;
    }



}
