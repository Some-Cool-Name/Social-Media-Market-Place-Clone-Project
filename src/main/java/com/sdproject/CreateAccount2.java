package com.sdproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateAccount2 extends AppCompatActivity {
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);

        // Calendar Code
        tvDate = findViewById(R.id.birthday_text2);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        com.sdproject.CreateAccount2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvDate.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        // **************************************************************


        // Spinner Code
        Spinner mySpinner = (Spinner) findViewById(R.id.gender_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(com.sdproject.CreateAccount2.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genders));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        // **************************************************************
    }
}