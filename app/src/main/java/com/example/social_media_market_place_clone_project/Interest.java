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

import java.util.ArrayList;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

 public class Interest extends AppCompatActivity {


    /* ThemedToggleButtonGroup themedButtonGroup =(ThemedToggleButtonGroup) findViewById(R.id.interests);
     ArrayList<String> Interests = new ArrayList();
     List<ThemedButton> Btn = new ArrayList();
    ThemedButton it;*/
    //ThemedToggleButtonGroup themedButtonGroup =(ThemedToggleButtonGroup) findViewById(R.id.interests);
     List<ThemedButton> Btn = new ArrayList();
     ArrayList<String> Interests = new ArrayList();
     //Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        ThemedButton themedButton;
        ThemedToggleButtonGroup themedButtonGroup = (ThemedToggleButtonGroup)findViewById(R.id.interests);



    }

   public void onContinue(View v){
       /*ThemedToggleButtonGroup themedButtonGroup = (ThemedToggleButtonGroup)findViewById(R.id.interests);

       Btn=themedButtonGroup.getSelectedButtons();

        for(int i=0;i<Btn.size();i++){
            String text = Btn.get(i).getText();
            Interests.add(text);
           // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }*/

       Intent intentSignIn = new Intent(Interest.this, HomeView.class);
       //intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       startActivity(intentSignIn);

    }



}