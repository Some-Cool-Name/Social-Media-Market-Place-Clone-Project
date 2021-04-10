package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    Button next;
    EditText email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Buttons and Edit Texts
        next = (Button) findViewById(R.id.sign_up_next_button);
        email = (EditText) findViewById(R.id.editTextSignUpEmail);
        password = (EditText) findViewById(R.id.editTextSignUpPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextSignUpConfirmPassword);
        // **************************************************************

        // Switch activities when buttons are pressed
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();
            }
        });
        // **************************************************************
    }

    public void doNext(){
        Intent intentNext = new Intent(SignUp.this, SignUp2.class);
        startActivity(intentNext);
    }
}