package com.example.alldata;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class photosrecycleradapter extends RecyclerView.Adapter<photosrecycleradapter.viewholder> {
    private List<String> itemList;
    private Context mContext;

    public photosrecycleradapter(List<String> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.photobox,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull photosrecycleradapter.viewholder holder, int position) {
           // bind
        if(itemList.size()<=position){
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(itemList.get(position));
        holder.imagecontent.setImageBitmap(bitmap);
        holder.imagetime.setText("working");

        holder.imagecontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Imageviewing.class);
                //Toast.makeText(mContext, itemList.get(position), Toast.LENGTH_SHORT).show();
                intent.putExtra("Bitmap",itemList.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.imagecontent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(itemList.get(position));
                                boolean deleted = file.delete();
                                if(deleted){
                                    itemList.remove(position);
                                    notifyDataSetChanged();
                                }else{
                                    Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imagecontent;
        TextView imagetime;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imagecontent = (ImageView) itemView.findViewById(R.id.photoboximageview);
            imagetime = itemView.findViewById(R.id.photoboxtime);
        }
    }
}
