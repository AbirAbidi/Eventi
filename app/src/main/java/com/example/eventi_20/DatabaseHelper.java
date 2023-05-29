package com.example.eventi_20;


import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.widget.Toast;

import java.util.Arrays;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

     String query = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + COLUMN_EMAIL + " TEXT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, context.getFilesDir() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(query);

        }catch (Exception e ){
            System.out.println(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String columns1 = Arrays.toString(columns);
        System.out.println(columns1);
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

}

