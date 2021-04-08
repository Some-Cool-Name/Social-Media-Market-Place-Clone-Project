package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    TextView birthday;
    Button register;
    EditText name, bio;
    Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        // Buttons, Edit Texts, Text Views and Spinners
        birthday = (TextView) findViewById(R.id.sign_up_birthday_text2);
        register = (Button) findViewById(R.id.sign_up_register_button);
        name = (EditText) findViewById(R.id.editTextSignUpName);
        bio = (EditText) findViewById(R.id.editTextSignUpBio);
        gender = (Spinner) findViewById(R.id.sign_up_gender_spinner);
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
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genders));

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
                        String date = day + "/" + month + "/" + year;
                        birthday.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        // **************************************************************
    }

    public void doRegister(){
        Intent intentRegister = new Intent(SignUp2.this, Profile.class);
        startActivity(intentRegister);
    }

}