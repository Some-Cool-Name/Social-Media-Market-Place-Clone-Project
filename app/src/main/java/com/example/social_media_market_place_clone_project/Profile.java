package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import android.widget.TextView;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SessionManager session = new SessionManager(Profile.this);
        session.checkLogin();
        HashMap<String,String> currentUser = session.getUserDetails();

        Toast.makeText(Profile.this,currentUser.get(session.EMAIL),Toast.LENGTH_SHORT).show();
        details = findViewById(R.id.displayCurrentUser);

        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        //HashMap<String, String> currentUser = sessionManager.getUserDetails();


        String email = currentUser.get(sessionManager.EMAIL);
        details.setText(email);
    }
}