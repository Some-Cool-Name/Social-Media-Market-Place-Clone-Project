package com.example.social_media_market_place_clone_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class HomeView extends AppCompatActivity {
    ImageView imageView;
    TextView nameAge, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        // Text Views for the Name, Age and location
        nameAge = (TextView) findViewById(R.id.home_name_text);
        location = (TextView) findViewById(R.id.home_location_text);

    }

    public void Profile(View v){
        Intent intentSignIn = new Intent(HomeView.this, Profile.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public void showMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

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
        Intent intent = new Intent(HomeView.this, Settings.class);
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




}