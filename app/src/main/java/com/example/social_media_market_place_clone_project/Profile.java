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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        details = findViewById(R.id.displayCurrentUser);
        logout= findViewById(R.id.logout_btn);
        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });
        Toast.makeText(Profile.this,currentUser.get(sessionManager.EMAIL),Toast.LENGTH_SHORT).show();

        String email = currentUser.get(sessionManager.EMAIL);
        details.setText(email);
    }
}