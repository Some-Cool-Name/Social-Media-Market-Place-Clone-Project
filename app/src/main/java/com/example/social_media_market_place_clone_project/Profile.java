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
    TextView name,age,location,bio;
    Button logout;
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

        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();

        name=findViewById(R.id.txtNameAge);

        AgeCalculator ageCalculator = new AgeCalculator();

        //age=findViewById(R.id.txtAge);
        //name.setText(currentUser.get("FULLNAME"));
        //age.setText(ageCalculator.calculateAge(currentUser.get("BIRTHDAY")).toString()); // create function to automatically calculate age

        String n = currentUser.get("FULLNAME");
        String a = ageCalculator.calculateAge(currentUser.get("BIRTHDAY")).toString();

        name.setText(n + ", " + a);


        location=findViewById(R.id.txtLocation);
        String url = currentUser.get("PROFILE_PICTURE");
        location.setText("Location");
        bio=findViewById(R.id.EdittxtBio) ;
        bio.setText(currentUser.get("BIO"));

        imageView = findViewById(R.id.profile_image);

        Toast.makeText(Profile.this,"Welcome",Toast.LENGTH_SHORT).show();
        loadImageFromUrl(url);
        System.out.print(url);


        /*
        logout=findViewById(R.id.logout_button);
        logout.setText("Logout");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });

        SessionManager session = new SessionManager(Profile.this);
        session.checkLogin();
        HashMap<String,String> currentUser = session.getUserDetails();
        Toast.makeText(Profile.this,currentUser.get(session.EMAIL),Toast.LENGTH_SHORT).show();
        */
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

    // a Function to automatically calculate the age given a string with the birthdate. //
   /* @RequiresApi(api = Build.VERSION_CODES.O)
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

    */
}