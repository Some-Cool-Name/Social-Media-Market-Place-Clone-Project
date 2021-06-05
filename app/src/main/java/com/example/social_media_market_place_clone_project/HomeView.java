package com.example.social_media_market_place_clone_project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.social_media_market_place_clone_project.ui.HomeViewCardAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;


public class HomeView extends AppCompatActivity {
    ImageView imageView;
    ImageButton cross;
    ArrayList<User> users = new ArrayList<>();

    private SwipeDeck cardStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        cross = findViewById(R.id.cross);
        imageView = findViewById(R.id.picture);

        // on below line we are initializing our array list and swipe deck.
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        try {
            users = getUsers();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final HomeViewCardAdapter adapter = new HomeViewCardAdapter(users, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter((Adapter) adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.

                //send disliked request

                SessionManager sessionManager = new SessionManager(HomeView.this);
                sessionManager.checkLogin();
                HashMap<String, String> currentUser = sessionManager.getUserDetails();


                Constants constants = new Constants();
                // Display Name and Age
                String n = currentUser.get("EMAIL");
                try {
                    dislike(n, users.get(position).getEmail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void cardSwipedRight(int position) {

                Constants constants = new Constants();

                SessionManager sessionManager = new SessionManager(HomeView.this);
                sessionManager.checkLogin();
                HashMap<String, String> currentUser = sessionManager.getUserDetails();

                // Display Name and Age
                String n = currentUser.get("EMAIL");
                backGroundLike(n,users.get(position).getEmail());
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(HomeView.this, "No more courses present", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swipped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });
    }

    public void Matches(View v) {
        Intent intent = new Intent(HomeView.this, Matches.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void like(String username, String liked){

        DatabaseQueries likeFeed = new DatabaseQueries();
        likeFeed.doLikeFeed(username, liked);

        // Request is finished

    }
    private void backGroundLike(String username, String like) {
        runOnUiThread(new Runnable(){
            public void run() {
                like(username, like);
            }
        });
    }
    public void dislike(String username, String disliked) throws JSONException {
        DatabaseQueries dislikeFeed = new DatabaseQueries();
        dislikeFeed.doDislikeFeed(username, disliked);

        // Request is finished

    }

    public void Chat(View v) {
        Intent intent = new Intent(HomeView.this, Users.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Profile(View v) {
        Intent intentSignIn = new Intent(HomeView.this, Profile.class);
        //intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }


    public ArrayList getUsers() throws JSONException {

        SessionManager sessionManager = new SessionManager(HomeView.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();

        // Display Name and Age
        String n = currentUser.get("EMAIL");
        DatabaseQueries getFeedUsers = new DatabaseQueries();


        return getFeedUsers.getUsersFeed(n);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }


}