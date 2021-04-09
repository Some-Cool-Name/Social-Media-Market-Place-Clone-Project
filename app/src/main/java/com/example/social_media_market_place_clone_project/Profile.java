package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        details = findViewById(R.id.displayCurrentUser);

        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();


        String email = currentUser.get(sessionManager.EMAIL);
        details.setText(email);
    }
}