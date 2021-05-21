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

import java.util.ArrayList;

public class InterestAdapter extends ArrayAdapter<String> {
    public InterestAdapter(@NonNull Context context, ArrayList<String> inerests) {
        super(context, 0, inerests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        String s = getItem(position);

        TextView tv = listitemView.findViewById(R.id.interest_item_text);

        tv.setText(s);
        return listitemView;
    }
}
