package com.example.social_media_market_place_clone_project.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.social_media_market_place_clone_project.R;
import com.example.social_media_market_place_clone_project.User;

import java.util.ArrayList;

public class HomeViewCardAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<User> userData;
    private Context context;

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

        // Load Image
        ((ImageView) v.findViewById(R.id.UserPicImageView)).setImageResource(R.drawable.messi);
        return v;
    }
}
