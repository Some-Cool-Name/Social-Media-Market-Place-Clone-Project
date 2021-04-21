package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditProfile extends AppCompatActivity {
    View back;
    Button save;
    EditText bio, interests, location;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Buttons on the settings page
        back = (View) findViewById(R.id.back_button);
        save = (Button) findViewById(R.id.save_button);
        bio = (EditText) findViewById(R.id.editTextBio);
        interests = (EditText) findViewById(R.id.editTextInterests);
        location = (EditText) findViewById(R.id.editTextLocation);

        profilePicture = (ImageView) findViewById(R.id.sign_up_profile_picture_background);

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
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);
    }

    public void doSave() {
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);
    }
}