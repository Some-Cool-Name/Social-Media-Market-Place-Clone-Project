package com.example.social_media_market_place_clone_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.social_media_market_place_clone_project.EditProfile;
import com.example.social_media_market_place_clone_project.R;
import com.example.social_media_market_place_clone_project.Settings;

public class SecuritySettings extends AppCompatActivity {
    EditText currentPassword, newPassword, confirmNewPassword;
    TextView forgotPassword;
    Button save;
    View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);

        // Buttons on the settings page
        back = (View) findViewById(R.id.back_button);
        save = (Button) findViewById(R.id.save_button_settings);
        currentPassword = (EditText) findViewById(R.id.editTextCurrentPasswordSettings);
        newPassword = (EditText) findViewById(R.id.editTextNewPasswordSettings);
        confirmNewPassword = (EditText) findViewById(R.id.editTextConfirmPasswordSettings);
        forgotPassword = (TextView) findViewById(R.id.forgot_password_text);

        // What happens when the buttons are clicked
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();
            }
        });
    }

    public void doBack() {
        Intent intent = new Intent(SecuritySettings.this, Settings.class);
        startActivity(intent);
    }

    public void doSave() {
        Intent intent = new Intent(SecuritySettings.this, Settings.class);
        startActivity(intent);
    }
}