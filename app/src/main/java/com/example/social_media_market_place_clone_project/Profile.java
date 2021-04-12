package com.example.social_media_market_place_clone_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;


public class Profile extends AppCompatActivity {
    TextView name,age,location,bio;
    Button logout;

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
        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();

        name=findViewById(R.id.txtName);
        name.setText(currentUser.get("FULLNAME"));
        age=findViewById(R.id.txtAge);
        age.setText(calculateAge(currentUser.get("BIRTHDAY")).toString()); // create function to automatically calculate age
        location=findViewById(R.id.txtLocation);
        location.setText("Braamfontein");
        bio=findViewById(R.id.EdittxtBio) ;
        bio.setText("Hi, I'm new here");

        logout=findViewById(R.id.logout_button);
        logout.setText("Logout");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });
        Toast.makeText(Profile.this,"Welcome",Toast.LENGTH_SHORT).show();


       /* SessionManager session = new SessionManager(Profile.this);
        session.checkLogin();
        HashMap<String,String> currentUser = session.getUserDetails();
        Toast.makeText(Profile.this,currentUser.get(session.EMAIL),Toast.LENGTH_SHORT).show();*/
    }

    // a Function to automatically calculate the age given a string with the birthdate. //
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Integer calculateAge(String birthDate){
        // Convert birthdayString to Integers //
        int bYear = Integer.parseInt(String.valueOf(birthDate.charAt(6)))*1000
                +Integer.parseInt(String.valueOf(birthDate.charAt(7)))*100
                +Integer.parseInt(String.valueOf(birthDate.charAt(8)))*10
                +Integer.parseInt(String.valueOf(birthDate.charAt(9)));
        int bMonth = Integer.parseInt(String.valueOf(birthDate.charAt(3)))*10
                + Integer.parseInt(String.valueOf(birthDate.charAt(4)));
        int bDay = Integer.parseInt(String.valueOf(birthDate.charAt(0)))*10
                +Integer.parseInt(String.valueOf(birthDate.charAt(1)));

        LocalDate birthday= LocalDate.of(bYear,bMonth,bDay);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(birthday, now);
        return diff.getYears();
    }
}