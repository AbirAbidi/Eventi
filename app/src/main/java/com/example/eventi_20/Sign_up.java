package com.example.eventi_20;

import static com.example.eventi_20.DatabaseHelper.COLUMN_EMAIL;
import static com.example.eventi_20.DatabaseHelper.COLUMN_PASSWORD;
import static com.example.eventi_20.DatabaseHelper.COLUMN_USERNAME;
import static com.example.eventi_20.DatabaseHelper.DATABASE_NAME;
import static com.example.eventi_20.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Sign_up extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        Button Done = findViewById(R.id.button);
        TextView login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(Sign_up.this,Sign_in.class);
                startActivity(j);
                overridePendingTransition(R.anim.a1, R.anim.a2);
                finish();
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (checkUserExisting(email10)){
//                    Toast.makeText(Sign_up.this,"This email is already used !",Toast.LENGTH_LONG).show();
//                }else if (pass10.equals(pass20) == false){
//                    Toast.makeText(Sign_up.this,"Password incorrect !",Toast.LENGTH_LONG).show();
//            }else if (pass10.isEmpty() || pass20.isEmpty() || email10.isEmpty() || username10.isEmpty()){
//                    Toast.makeText(Sign_up.this, "Empty field !", Toast.LENGTH_SHORT).show();
//                } else if ((pass10.equals(pass20) ) && (email10.isEmpty() == false) &&( username10.isEmpty() == false)) {
//                    String username1= String.valueOf(username.getEditText().getText());
//                    String email1 = String.valueOf(email.getEditText().getText());
//                    String pass3 = pass1.getContext().toString();
//                    addUser(username1,email1,pass3);
//                    Intent i = new Intent(Sign_up.this,Home_page.class);
//                    i.putExtra("username",username1 );
//                    i.putExtra("email",email1 );
//                    startActivity(i);
//                    finish();
//                }
                //username
                TextInputLayout textInputLayout = findViewById(R.id.username);
                TextInputEditText textInputEditText = (TextInputEditText) textInputLayout.getEditText();
                String usernameF = textInputEditText.getText().toString();
                // email
                TextInputLayout textInputLayout1 = findViewById(R.id.email);
                TextInputEditText textInputEditText1 = (TextInputEditText) textInputLayout.getEditText();
                String emailF = textInputEditText.getText().toString();
                //pass1
                TextInputLayout textInputLayout2 = findViewById(R.id.password1);
                TextInputEditText textInputEditText2 = (TextInputEditText) textInputLayout.getEditText();
                String passwordF = textInputEditText.getText().toString();
                //pass2
                TextInputLayout textInputLayout3 = findViewById(R.id.password2);
                TextInputEditText textInputEditText3 = (TextInputEditText) textInputLayout.getEditText();
                String repasswordF = textInputEditText.getText().toString();
                if (emailF.isEmpty() || usernameF.isEmpty() || passwordF.isEmpty() || repasswordF.isEmpty()){
                    Toast.makeText(Sign_up.this, "Empty field", Toast.LENGTH_SHORT).show();
                } else if (checkUserExisting(emailF)) {
                    Toast.makeText(Sign_up.this, "User already exists", Toast.LENGTH_SHORT).show();
                } else  {
                    if (passwordF.equals(repasswordF)==false){
                        Toast.makeText(Sign_up.this, "Rewrite password", Toast.LENGTH_SHORT).show();
                    } else  {
                        addUser(usernameF,emailF,passwordF);
                        Intent i = new Intent(Sign_up.this,Home_page.class);
                        i.putExtra("username",usernameF );
                        i.putExtra("email",emailF );
                        startActivity(i);
                        finish();
                    }
                }

            }
        });

    }
    public void addUser(String username, String email, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
       long id= db.insert("users", null, values);
        if (id == -1 || !checkUserExisting(email)) {
            // Error occurred while inserting data
            Toast.makeText(Sign_up.this,"insertion error",Toast.LENGTH_LONG).show();
        } else {
            // Data was inserted successfully
            Toast.makeText(Sign_up.this,"data inserted",Toast.LENGTH_LONG).show();

        }

// Close the database connection
        db.close();
    }
    public  boolean checkUserExisting(String email){
        SQLiteDatabase db =dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT 1 FROM users WHERE email=?", new String[]{email});

        boolean exists = cursor.moveToFirst();

        cursor.close();
        System.out.println(exists);
        return exists;
    }

}