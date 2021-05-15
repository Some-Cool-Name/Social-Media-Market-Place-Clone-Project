package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    EditText searchBar;
    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Search bar and button
        searchBar = (EditText) findViewById(R.id.match_search_EditText);
        searchButton = (ImageButton) findViewById(R.id.match_search_Button);

        // List of matches
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        List<ChatContact> matchList = new ArrayList<>();

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String username, lastMessage;
        username = "Tumza";
        lastMessage = "Testing";
        matchList.add(new ChatContact(username, lastMessage));

        for (int i = 0; i < 10; i++) {
            username = "Tumza" + i;
            lastMessage = "Testing";
            matchList.add(new ChatContact(username, lastMessage));
        }


        // define an adapter
        mAdapter = new ChatAdapter(matchList);
        recyclerView.setAdapter(mAdapter);

        // Listen for typing on search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });



    }

    public void Home(View v){
        Intent intentSignIn = new Intent(Chat.this, HomeView.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public void Profile(View v){
        Intent intent = new Intent(Chat.this, Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Matches(View v){
        Intent intent = new Intent(Chat.this, Matches.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}