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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;

import okhttp3.HttpUrl;


public class Profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView name, location, bio, int1,int2,int3,int4,int5, editInt;
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
        editInt= findViewById(R.id.editInt);
        AgeCalculator ageCalculator = new AgeCalculator();

        // Display Name and Age
        String n = currentUser.get("FULLNAME");
        String a = ageCalculator.calculateAge(currentUser.get("BIRTHDAY")).toString();

        name.setText(n + ", " + a);

        int1 = findViewById(R.id.txtInterest1);
        int2 = findViewById(R.id.txtInterest2);
        int3 = findViewById(R.id.txtInterest3);
        int4 = findViewById(R.id.txtInterest4);
        int5 = findViewById(R.id.txtInterest5);
        location = findViewById(R.id.txtLocation);
        location.setText("Location");

        bio = findViewById(R.id.EdittxtBio) ;
        bio.setText(currentUser.get("BIO"));

        String url = currentUser.get("PROFILE_PICTURE");
        imageView = findViewById(R.id.profile_image);
        editInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditInt = new Intent(Profile.this, Interest.class);
                //intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentEditInt);
            }
        });
        loadImageFromUrl(url);
        System.out.print(url);
        try {
            getInterests();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(Profile.this,"Welcome",Toast.LENGTH_SHORT).show();
    }

    public void Home(View v){
        Intent intentSignIn = new Intent(Profile.this, HomeView.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public void Matches(View v){
        Intent intent = new Intent(Profile.this, Matches.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Chat(View v){
        Intent intent = new Intent(Profile.this, Users.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    public void getInterests() throws JSONException {
        ArrayList<User> users = new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();
        SessionManager sessionManager = new SessionManager(Profile.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();


        // Display Name and Age
        String n = currentUser.get("EMAIL");

        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAGetInterest.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username", n);
        String url = urlBuilder.build().toString();
        request.execute(url);


        while (request.Result.equals("Waiting")) {
            System.out.print("waiting");
            // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

        }


        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("Interest").toString()); // extract the login credentials array
        JSONObject userCredentials = jsonArray.getJSONObject(0);
        int1.setText(userCredentials.getString("interest_1"));
        int2.setText(userCredentials.getString("interest_2"));
        int3.setText(userCredentials.getString("interest_3"));
        int4.setText(userCredentials.getString("interest_4"));
        int5.setText(userCredentials.getString("interest_5"));
    }
}