package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class Matches extends AppCompatActivity {
    GridView matchesGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // Adding Data to the grid view
        matchesGV = findViewById(R.id.matches_gridview);

        ArrayList<MiniProfile> matchList = new ArrayList<MiniProfile>();
        matchList.add(new MiniProfile("User1234", "18", R.id.add));
        matchList.add(new MiniProfile("User5469", "35", R.drawable.messi));
        matchList.add(new MiniProfile("User841", "26", R.drawable.messi));
        matchList.add(new MiniProfile("User9876", "24", R.drawable.messi));
        matchList.add(new MiniProfile("User1234", "18", R.id.add));
        matchList.add(new MiniProfile("User5469", "35", R.drawable.messi));
        matchList.add(new MiniProfile("User841", "26", R.drawable.messi));
        matchList.add(new MiniProfile("User9876", "24", R.drawable.messi));

        MatchGVAdapter adapter = new MatchGVAdapter(this, matchList);
        matchesGV.setAdapter(adapter);
        // ************************************************************************
    }

    public void Home(View v){
        Intent intentSignIn = new Intent(Matches.this, HomeView.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public void Profile(View v){
        Intent intent = new Intent(Matches.this, Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Chat(View v){
        Intent intent = new Intent(Matches.this, Chat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}