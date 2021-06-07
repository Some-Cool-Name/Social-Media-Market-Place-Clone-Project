package com.example.social_media_market_place_clone_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.social_media_market_place_clone_project.HomeView;
import com.example.social_media_market_place_clone_project.R;
import com.example.social_media_market_place_clone_project.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeViewCardAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<User> userData;
    private Context context;
    private  ImageView imageView;

    // on below line we have created constructor for our variables.
    public HomeViewCardAdapter(ArrayList<User> userData, Context context) {
        this.userData = userData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return userData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return userData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        ((TextView) v.findViewById(R.id.UserNameTextView)).setText(userData.get(position).getName());
        ((TextView) v.findViewById(R.id.UserBioTextView)).setText(userData.get(position).getBio()+"\n" + userData.get(position).getLocation()+"\n"+userData.get(position).getDistanceFromUser().intValue()+" km away");
        ((TextView) v.findViewById(R.id.userInterest1)).setText(userData.get(position).getInterests().get(0));
        ((TextView) v.findViewById(R.id.userInterest2)).setText(userData.get(position).getInterests().get(1));
        ((TextView) v.findViewById(R.id.userInterest3)).setText(userData.get(position).getInterests().get(2));

        // Load Image
        imageView = v.findViewById(R.id.UserPicImageView);
        loadImageFromUrl(userData.get(position).getImageUrl());
       // ((ImageView) v.findViewById(R.id.UserPicImageView)).setImageResource(R.drawable.messi);
        return v;
    }


    private void loadImageFromUrl(String url) {
        Picasso.with(context).load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }
}