package com.example.eventi_20;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventi_20.ui.DatabaseHelperPost;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context ;
    private ArrayList event_name;
    private ArrayList event_place;
    private ArrayList event_date;
    private ArrayList<byte[]> image ;
    private DatabaseHelperPost dbHelper;

    public CustomAdapter(Context context, ArrayList event_name, ArrayList event_date, ArrayList event_place, ArrayList image){
        this.context=context;
        this.event_date = event_date;
        this.event_name = event_name;
        this.event_place = event_place ;
        this.image = image;
        dbHelper = new DatabaseHelperPost(context);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.widget,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(event_name.get(position)));
        holder.date.setText(String.valueOf(event_date.get(position)));
        holder.place.setText(String.valueOf(event_place.get(position)));
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve the image data from the database using the image ID
        String[] args = new String[] { String.valueOf(image.get(position)) };

        String query = "SELECT image FROM events ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

                byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                System.out.println(imageData);
                // Convert the byte array to a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                // Set the Bitmap on the ImageView
                holder.image.setImageBitmap(bitmap);

        } else {
            Log.e("TAG", "Query returned no results: " + query);
        }
        cursor.close();
        db.close();
    }

    @Override
    public int getItemCount() {
        return event_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name ,  date , place ;
        ImageView image ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            place = itemView.findViewById(R.id.place);
            image = itemView.findViewById(R.id.widget_image);

        }
    }
}
