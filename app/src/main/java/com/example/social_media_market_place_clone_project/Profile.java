package com.example.social_media_market_place_clone_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;


public class Profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView name, location, bio;
    ImageView imageView;

    // Disable back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);

        //I added this if statement to keep the selected fragment when rotating the device


        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();

        name = findViewById(R.id.txtNameAge);

        AgeCalculator ageCalculator = new AgeCalculator();

        // Display Name and Age
        String n = currentUser.get("FULLNAME");
        String a = ageCalculator.calculateAge(currentUser.get("BIRTHDAY")).toString();

        name.setText(n + ", " + a);


        location = findViewById(R.id.txtLocation);
        location.setText("Location");

        bio = findViewById(R.id.EdittxtBio) ;
        bio.setText(currentUser.get("BIO"));

        String url = currentUser.get("PROFILE_PICTURE");
        imageView = findViewById(R.id.profile_image);

        loadImageFromUrl(url);
        System.out.print(url);

        Toast.makeText(Profile.this,"Welcome",Toast.LENGTH_SHORT).show();
    }

    public void onHome(View v){
        Intent intentSignIn = new Intent(Profile.this, HomeView.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    // Drop down menu
    public void showMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_item:
                openMenu();
                return true;

            default:
                return false;
        }
    }

    public void openMenu(){
        Intent intent = new Intent(Profile.this, Settings.class);
        startActivity(intent);
    }
    // **************************************************************

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }
}