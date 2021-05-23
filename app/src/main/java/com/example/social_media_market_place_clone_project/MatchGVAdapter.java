package com.example.social_media_market_place_clone_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MatchGVAdapter extends ArrayAdapter<MiniProfile> {
    private Context context;
    ImageView imageView;
    String ImageUrl;
    public MatchGVAdapter(@NonNull Context context, ArrayList<MiniProfile> courseModelArrayList, String imageUrl) {
        super(context, 0, courseModelArrayList);
        this.context = context;
        this.ImageUrl = imageUrl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        MiniProfile miniProfile = getItem(position);

        TextView infoTV = listitemView.findViewById(R.id.profile_name_text);
        ImageView picture = listitemView.findViewById(R.id.match_picture);


        //imageView = listitemView.findViewById(R.id.match_picture);
        String info = miniProfile.getUsername() + ", " + miniProfile.getUserAge();
        infoTV.setText(info);
        //picture.setImageResource(miniProfile.getImageId());
        return listitemView;
    }


}
