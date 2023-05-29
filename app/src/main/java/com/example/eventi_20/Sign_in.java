package com.example.eventi_20;


import static com.example.eventi_20.DatabaseHelper.COLUMN_EMAIL;
import static com.example.eventi_20.DatabaseHelper.COLUMN_PASSWORD;
import static com.example.eventi_20.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class Sign_in extends AppCompatActivity {
    private TextInputLayout usernameEditText, passwordEditText;
    private Button signInButton;

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // hide the default banner
        getSupportActionBar().hide();
        // initialize the database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        // email and password
        // other variables
        TextView signup = findViewById(R.id.textView5);
        Button Done = findViewById(R.id.button2);
        Done.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        TextView signin = findViewById(R.id.textView);
        // underline sign up
        signup.setPaintFlags(signup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        // Go to SIGN UP page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sign_in.this,Sign_up.class);
                startActivity(i);
                overridePendingTransition(R.anim.a1, R.anim.a2);
                finish();
            }
        });

        // DONE AUTHETIFICATION
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout = findViewById(R.id.email);
                TextInputEditText textInputEditText = (TextInputEditText) textInputLayout.getEditText();
                String email = textInputEditText.getText().toString();
                TextInputLayout textInputLayout1 = findViewById(R.id.password);
                TextInputEditText textInputEditText1 = (TextInputEditText) textInputLayout.getEditText();
                String password = textInputEditText.getText().toString();
                if (check(email,password) == 0){
                    Toast.makeText(Sign_in.this, "goooood", Toast.LENGTH_SHORT).show();
                } else if (check(email,password)== 1) {
                    Toast.makeText(Sign_in.this, "Wrong password ", Toast.LENGTH_SHORT).show();
                } else if (check(email,password)==2) {
                    Toast.makeText(Sign_in.this, "User not found", Toast.LENGTH_SHORT).show();
                }

            }

        });




}
    List<String> column_email = new ArrayList<>();
    List<String> column_password = new ArrayList<>();
    List<String> column_username = new ArrayList<>();


    public String return_username(int index){
       return column_username.get(index);
    };

    public int check(String email,String password){
        Cursor cursor = db.rawQuery("SELECT * FROM users",null);
        while(cursor.moveToNext()){
            String emails = cursor.getString((cursor.getColumnIndexOrThrow("email")));
            String passwords = cursor.getString((cursor.getColumnIndexOrThrow("password")));
            String usernames = cursor.getString((cursor.getColumnIndexOrThrow("username")));
            column_username.add(usernames);
            column_email.add(emails);
            column_password.add(passwords);
        }
        if (column_email.contains(email)) {
            int index = column_email.indexOf(email);;
            if (column_password.get(index).equals(password)) {
                Intent i = new Intent(Sign_in.this,Home_page.class);
                i.putExtra("username",return_username(index) );
                i.putExtra("email",email );
                startActivity(i);
                finish();
                return 0;
            } else if (column_password.get(index).equals(password) == false) {
                return 1;
            }
        }
        Toast.makeText(this, "Try to sign up", Toast.LENGTH_SHORT).show();
        // Handle the case where email is not found
        return 2;
    }

}

