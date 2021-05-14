package com.example.social_media_market_place_clone_project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.HttpUrl;

public class HomeView extends AppCompatActivity {
    ImageView imageView;
    TextView nameAge, location;
    ImageButton cross;
    ArrayList<User> users = new ArrayList<>();
    int index =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        // Text Views for the Name, Age and location
        nameAge = (TextView) findViewById(R.id.home_name_text);
        location = (TextView) findViewById(R.id.home_location_text);
        cross = findViewById(R.id.cross);
        imageView = findViewById(R.id.picture);

        try {

            users = getUsers();
            if(users!=null){
                nameAge.setText(users.get(index).getName());
                String url = users.get(index).getImageUrl();
                loadImageFromUrl(url);
                index ++;
            }

            cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameAge.setText(users.get(index).getName());
                    String url = users.get(index).getImageUrl();
                    loadImageFromUrl(url);
                    if(index< users.size()-1){
                        index ++;
                    }
                }
            });




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
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

    public void Profile(View v){
        Intent intentSignIn = new Intent(HomeView.this, Profile.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public ArrayList getUsers() throws JSONException {
        ArrayList<User> users = new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAFetch.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        String url = urlBuilder.build().toString();
        request.execute(url);


        while(request.Result.equals("Waiting")){
            System.out.print("waiting");
          // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

        }


        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("Details").toString()); // extract the login credentials array

        for(int i =0; i < jsonArray.length(); i ++){
            JSONObject userCredentials = jsonArray.getJSONObject(i);
            User newUser = new User();
            newUser.setName(userCredentials.getString("username"));
            newUser.setBio(userCredentials.getString("bio"));
            newUser.setImageUrl(userCredentials.getString("profile_picture"));
            users.add(newUser);

        }

        return users;
    }

    public void showMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.settings_item:
//                openMenu();
//                return true;
//
//            default:
//                return false;
//        }
//    }

//    public void openMenu(){
//        Intent intent = new Intent(HomeView.this, Settings.class);
//        startActivity(intent);
//    }
    // **************************************************************





}