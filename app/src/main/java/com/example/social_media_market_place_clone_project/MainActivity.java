package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button signIn, signUp;
    Map config = new HashMap();

//    TODO: Vhugala - insert your cloudinary details here
    //    sets up cloud connection
    private void configCloudinary() {
        config.put("cloud_name", "dkctv74ue");
        config.put("api_key", "421896893816688");
        config.put("api_secret", "pumaVpw63KKXxpRi-ZfLzwo7eck");
        MediaManager.init(MainActivity.this, config);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        signIn = (Button) findViewById(R.id.sign_in_button);
        signUp = (Button) findViewById(R.id.sign_up_button);
        // **************************************************************
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firsttime",false)){
            configCloudinary();
            SharedPreferences.Editor editor= prefs.edit();
            editor.putBoolean("firsttime", true);
            editor.commit();
        }


        // Switch activities when buttons are pressed
        SessionManager sessionManager = new SessionManager(MainActivity.this);
        if(sessionManager.checkLogin()){
            goToProfile();
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