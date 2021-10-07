package com.example.alldata;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class textsrecycleradapter extends RecyclerView.Adapter<textsrecycleradapter.ViewHolder> {
    private Context mycontext;
    private Cursor mycursor;
     private SQLiteDatabase mydatabase;
     private appdatabase appdatabase;
    public textsrecycleradapter(Context context, Cursor cursor){
        mycontext = context;
        mycursor = cursor;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        View view = inflater.inflate(R.layout.messagebox,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(textsrecycleradapter.ViewHolder holder, int position) {
        if(!mycursor.moveToPosition(position)){
            return;
        }
        String content = mycursor.getString(mycursor.getColumnIndex(Contractfortables.textstable.COLUMN_TEXT));
        long time = mycursor.getLong(mycursor.getColumnIndex(Contractfortables.textstable.COLUMN_DATE));
        int id = mycursor.getInt((mycursor.getColumnIndex(Contractfortables.textstable._ID)));
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time);  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "/" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);
        String timeinmin = date + "  " + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) ;
        holder.messageboxcontent.setText(content);
        holder.messageboxtime.setText(timeinmin);

        holder.messageboxcontent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                appdatabase = new appdatabase(mycontext);
                mydatabase = appdatabase.getWritableDatabase();
                ClipboardManager clipboard = (ClipboardManager) mycontext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied text.", content);
                clipboard.setPrimaryClip(clip);
                showdeletedialog(id);
                //Toast.makeText(mycontext, "long pressed " + id, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void showdeletedialog(int id) {
        new AlertDialog.Builder(mycontext)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String whereClause = "_ID=?";
                        String[] whereArgs = new String[] { String.valueOf(id) };
                        mydatabase.delete(Contractfortables.textstable.TABLE_NAME,whereClause,whereArgs);
                        //Toast.makeText(mycontext, "deleting....", Toast.LENGTH_SHORT).show();
                        Cursor cu = mydatabase.query(
                                Contractfortables.textstable.TABLE_NAME,
                                null,
                                null
                                ,null
                                ,null
                                ,null
                                ,Contractfortables.textstable._ID + " DESC");
                        updateCursor(cu);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public int getItemCount() {

        return mycursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageboxcontent;
        TextView messageboxtime;
        public ViewHolder(View itemView) {
            super(itemView);
            messageboxcontent = itemView.findViewById(R.id.messageboxcontent);
            messageboxtime = itemView.findViewById(R.id.messageboxtime);

        }

    }
    public void updateCursor(Cursor newone){
        //Toast.makeText(mycontext, "hello updatecursor", Toast.LENGTH_SHORT).show();
        if(mycursor !=null){
            mycursor.close();
        }
        mycursor = newone;
        if(mycursor!=null){
            notifyDataSetChanged();
        }
    }

}
