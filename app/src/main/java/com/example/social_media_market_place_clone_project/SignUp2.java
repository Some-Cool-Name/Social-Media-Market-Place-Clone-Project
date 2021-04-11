package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    TextView birthday;
    Button register;
    EditText name, bio;
    Spinner gender;
    ImageView profilePicture;
    int numPics = 1;
    Uri uri;
    String date; // DD MONTH YYYY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        // Buttons, Edit Texts, Text Views, Image Views and Spinners
        birthday = (TextView) findViewById(R.id.sign_up_birthday_text2);
        register = (Button) findViewById(R.id.sign_up_register_button);
        name = (EditText) findViewById(R.id.editTextSignUpName);
        bio = (EditText) findViewById(R.id.editTextSignUpBio);
        gender = (Spinner) findViewById(R.id.sign_up_gender_spinner);
        profilePicture = (ImageView) findViewById(R.id.sign_up_profile_picture_background);
        // **************************************************************

        // Switch activities when buttons are pressed
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
        // **************************************************************

        // Spinner Code
        Spinner mySpinner = (Spinner) findViewById(R.id.sign_up_gender_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(com.example.social_media_market_place_clone_project.SignUp2.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.genders));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        // **************************************************************

        // Calendar for Birthday
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        com.example.social_media_market_place_clone_project.SignUp2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String monthString = getMonth(month);
                        date = day + " " + monthString + " " + year;
                        birthday.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        // **************************************************************

        // Choose Image
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, numPics);
            }
        });
        // **************************************************************
    }

    public void doRegister(){
        Intent intentRegister = new Intent(SignUp2.this, Profile.class);
        startActivity(intentRegister);
    }

    public String getMonth(int i){
        String m;

        switch(i){
            case 1:
                m = "January";
                break;

            case 2:
                m = "February";
                break;

            case 3:
                m = "March";
                break;

            case 4:
                m = "April";
                break;

            case 5:
                m = "May";
                break;

            case 6:
                m = "June";
                break;

            case 7:
                m = "July";
                break;

            case 8:
                m = "August";
                break;

            case 9:
                m = "September";
                break;

            case 10:
                m = "October";
                break;

            case 11:
                m = "November";
                break;

            case 12:
                m = "December";
                break;

            default:
                m = "Month";
        }

        return m;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}