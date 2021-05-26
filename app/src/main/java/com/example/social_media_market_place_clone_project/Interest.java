 package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.social_media_market_place_clone_project.R;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;
import okhttp3.HttpUrl;

 public class Interest extends AppCompatActivity {


    /* ThemedToggleButtonGroup themedButtonGroup =(ThemedToggleButtonGroup) findViewById(R.id.interests);
     ArrayList<String> Interests = new ArrayList();
     List<ThemedButton> Btn = new ArrayList();
    ThemedButton it;*/
    //ThemedToggleButtonGroup themedButtonGroup =(ThemedToggleButtonGroup) findViewById(R.id.interests);
     List<ThemedButton> Btn = new ArrayList();
     ArrayList<String> InterestsStore = new ArrayList();
     public ArrayList<String> Interests = new ArrayList();
     SignUp signUp ;
     String email = signUp.emailExport;
     //Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        ThemedToggleButtonGroup themedButtonGroup = (ThemedToggleButtonGroup)findViewById(R.id.interests);
        themedButtonGroup.setOnSelectListener(themedButton1 -> {
            String text = themedButton1.getText();
            InterestsStore.add(text);
            //Toast.makeText(this,"Selected "+text , Toast.LENGTH_SHORT).show();
            return null;
        });


    }


   public void onContinue(View v) throws JSONException {
      int SizeArray = InterestsStore.size();

      if(SizeArray>4) {
          --SizeArray;
          for (int i = 0; i < 5; i++) {
              Interests.add(InterestsStore.get(SizeArray));
              --SizeArray;
          }
          //do The http request

            sendInterests();

          Intent intentSignIn = new Intent(Interest.this, Profile.class);
          //intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intentSignIn);
      }
      else{Toast.makeText(this,"Please Select Five Interests",Toast.LENGTH_LONG).show();}

    }
     public void sendInterests() throws JSONException {
         SessionManager sessionManager = new SessionManager(Interest.this);
         sessionManager.checkLogin();
         HashMap<String, String> currentUser = sessionManager.getUserDetails();


         // Display Name and Age
         String n = currentUser.get("EMAIL");

         AsyncNetwork request = new AsyncNetwork();
         String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAInterest.php";
         HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
         urlBuilder.addQueryParameter("username", n);
         urlBuilder.addQueryParameter("interest_1", Interests.get(0));
         urlBuilder.addQueryParameter("interest_2", Interests.get(1));
         urlBuilder.addQueryParameter("interest_3", Interests.get(2));
         urlBuilder.addQueryParameter("interest_4", Interests.get(3));
         urlBuilder.addQueryParameter("interest_5", Interests.get(4));
         String url = urlBuilder.build().toString();
         request.execute(url);


         while (request.Result.equals("Waiting")) {
             System.out.print("waiting");
             // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

         }






     }



}