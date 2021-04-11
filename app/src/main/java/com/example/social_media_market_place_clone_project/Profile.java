package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class Profile extends AppCompatActivity {
    TextView details;
    Button logout;

    // Disable back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //details = findViewById(R.id.displayCurrentUser);
        //logout= findViewById(R.id.button2);
        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();

        Toast.makeText(Profile.this,currentUser.get(sessionManager.EMAIL),Toast.LENGTH_SHORT).show();

        String email = currentUser.get(sessionManager.EMAIL);
        //details.setText(email);


       /* SessionManager session = new SessionManager(Profile.this);
        session.checkLogin();
        HashMap<String,String> currentUser = session.getUserDetails();

        Toast.makeText(Profile.this,currentUser.get(session.EMAIL),Toast.LENGTH_SHORT).show();*/
    }
}