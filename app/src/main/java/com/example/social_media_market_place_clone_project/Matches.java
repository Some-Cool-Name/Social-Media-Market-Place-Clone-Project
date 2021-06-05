package com.example.social_media_market_place_clone_project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.HttpUrl;

public class Matches extends AppCompatActivity {
    GridView matchesGV;
    String noMatches ;
    TextView xlikes;
    ImageView imageView;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // Adding Data to the grid view

        matchesGV = findViewById(R.id.matches_gridview);
        xlikes = findViewById(R.id.number_likes_text);
        imageView = findViewById(R.id.match_picture);
        try {
            users =getMatch();
            xlikes.setText(noMatches+ " Likes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<MiniProfile> matchList = new ArrayList<MiniProfile>();

        for(int i =0;i< users.size();i++){
            matchList.add(new MiniProfile(users.get(i).getEmail(), "24", "https://res.cloudinary.com/dkctv74ue/image/upload/v1621549565/pc0l9hxwkvpxpugxupkh.jpg)"));

        }

        MatchGVAdapter adapter = new MatchGVAdapter(this, matchList,"https://res.cloudinary.com/dkctv74ue/image/upload/v1621549565/pc0l9hxwkvpxpugxupkh.jpg)");

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
        Intent intent = new Intent(Matches.this, Users.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public ArrayList<User>  getMatch() throws JSONException {
        SessionManager sessionManager = new SessionManager(Matches.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();
        // Display Name and Age
        String n = currentUser.get("EMAIL");
        ArrayList<User> users = new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();

        sessionManager.checkLogin();

        // Display Name and Age

        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAgetMatches.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",n);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            System.out.print("waiting");
            // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

        }

        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("matchedWith").toString()); // extract the login credentials array
        noMatches = wholeString.getString("count");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userCredentials = jsonArray.getJSONObject(i);
            User user = new User();
            user.setEmail(userCredentials.getString("E_mail"));
            user.setImageUrl(userCredentials.getString("Profile_Picture"));
            users.add(user);

        }

        return users;

    }

}