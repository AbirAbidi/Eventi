package com.example.eventi_20.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eventi_20.Home_page;
import com.example.eventi_20.R;

public class DatabaseHelperPost  extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Events.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "event_name";
    private static final String COLUMN_PLACE = "event_place";
    private static final String COLUMN_DATE = "event_date";



    public DatabaseHelperPost(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context ;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_DATE + " DATE, " +
                "image BLOB) ;" ;
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void add_data(String name , String place , String date , byte[] image){
      SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_PLACE,place);
        cv.put(COLUMN_DATE, date);
        cv.put("image", image);
        long result = db.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
        } else if ( name.isEmpty()|| place.isEmpty()|| date.isEmpty() || image.toString().isEmpty()) {
            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"success",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, Home_page.class);
            context.startActivity(i);
            ((Activity) context).overridePendingTransition(R.anim.a1, R.anim.a2);

        }
        db.close();

    }
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery (query,  null);
        }
        return cursor;
    };


}
