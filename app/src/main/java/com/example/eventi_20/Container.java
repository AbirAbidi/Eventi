package com.example.eventi_20;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventi_20.ui.DatabaseHelperPost;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class Container extends AppCompatActivity {
    private static final int GALLERY_REQ_CODE = 1000;

    private void saveImageToDatabase(byte[] image) {
        DatabaseHelperPost dbHelper = new DatabaseHelperPost(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("event_image", image);
        long newRowId = db.insert("Events", null, values);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Button import_btn = findViewById(R.id.button3);

        import_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent gallery = new Intent(Intent.ACTION_PICK);
              gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(gallery,GALLERY_REQ_CODE);

            }
        });






    }

    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQ_CODE) {
// for gallery
                ImageView ingGallery = findViewById(R.id.image);
                ingGallery.setImageURI(data.getData());
                Button btn_save = findViewById(R.id.btn_save);
                EditText name = findViewById(R.id.et_name);
                EditText place = findViewById(R.id.et_place);
                EditText date = findViewById(R.id.et_date);


                // Get the URI of the selected image
                Uri imageUri = data.getData();
                try {
                    // Convert the image to a byte array
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    byte[] imageData = IOUtils.toByteArray(inputStream);
                    // Set the selected image in the ImageView
                    ImageView imageView = findViewById(R.id.image);
                    imageView.setImageURI(imageUri);







                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseHelperPost db = new DatabaseHelperPost(Container.this);
                            db.add_data(name.getText().toString(),
                                    place.getText().toString(),
                                    date.getText().toString(),
                                    imageData
                            );



                            TextInputLayout textInputLayout3 = findViewById(R.id.text_input_layout3);
                            EditText textInput3 = findViewById(R.id.et_place);
                            TextInputLayout textInputLayout2 = findViewById(R.id.text_input_layout2);
                            EditText textInput2 = findViewById(R.id.et_date);
                            TextInputLayout textInputLayout1 = findViewById(R.id.text_input_layout1);
                            EditText textInput1 = findViewById(R.id.et_name);

                            //1
                            textInput1.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if (editable.toString().trim().isEmpty()) {
                                        textInputLayout1.setError("Input cannot be empty");
                                        textInputLayout1.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                                    } else {
                                        textInputLayout1.setError(null);
                                    }
                                }
                            });
                            //2
                            textInput2.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if (editable.toString().trim().isEmpty()) {
                                        textInputLayout2.setError("Input cannot be empty");
                                        textInputLayout2.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                                    } else {
                                        textInputLayout2.setError(null);
                                    }
                                }
                            });

//3

                            textInput3.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    // do nothing
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if (editable.toString().trim().isEmpty()) {
                                        textInputLayout3.setError("Input cannot be empty");
                                        textInputLayout3.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                                    } else {
                                        textInputLayout3.setError(null);
                                    }
                                }
                            });
                        }
                    });









                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        }}
}