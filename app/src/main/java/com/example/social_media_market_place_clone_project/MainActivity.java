package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        signIn = (Button) findViewById(R.id.sign_in_button);
        signUp = (Button) findViewById(R.id.sign_up_button);
        // **************************************************************

        // Switch activities when buttons are pressed
        SessionManager sessionManager = new SessionManager(MainActivity.this);
        if(sessionManager.checkLogin()){
            //goToProfile();
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignIn();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });
        // **************************************************************
    }

        public void doSignUp(){
            Intent intentSignUp = new Intent(this, SignUp.class);
            startActivity(intentSignUp);
        }

        public void doSignIn(){
            Intent intentSignIn = new Intent(this, SignIn.class);
            startActivity(intentSignIn);
        }
        public void goToProfile(){
            Intent viewProfile = new Intent(this, Profile.class);
            viewProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(viewProfile);
        }
}