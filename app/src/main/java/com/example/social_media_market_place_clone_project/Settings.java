package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

public class Settings extends AppCompatActivity {
    RelativeLayout editProfile, security, theme, help, logout;
    View back;
    TextView displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Buttons on the settings page
        back = (View) findViewById(R.id.back_button);
        editProfile = (RelativeLayout) findViewById(R.id.edit_layout);
        security = (RelativeLayout) findViewById(R.id.security_layout);
        theme = (RelativeLayout) findViewById(R.id.theme_layout);
        help = (RelativeLayout) findViewById(R.id.help_layout);
        logout = (RelativeLayout) findViewById(R.id.logout_layout);
        displayName = findViewById(R.id.name_text);
        SessionManager sessionManager = new SessionManager(Settings.this);
        HashMap<String, String> currentUser = sessionManager.getUserDetails();
        String name = currentUser.get("FULLNAME");
        displayName.setText(name);
        // What happens when the buttons are clicked
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecurity();
            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTheme();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelp();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogOut();
            }
        });
    }

    public void doBack() {
        Intent intent = new Intent(Settings.this, Profile.class);
        startActivity(intent);
    }

    public void openEditProfile(){
        Intent intent = new Intent(Settings.this, EditProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void openSecurity(){}

    public void openTheme(){}

    public void openHelp(){}

    public void doLogOut(){
        SessionManager sessionManager = new SessionManager(Settings.this);
        sessionManager.logoutUser();
    }
}