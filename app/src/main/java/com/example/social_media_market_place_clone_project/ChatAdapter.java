package com.example.social_media_market_place_clone_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> implements Filterable {
    private List<ChatContact> matches;
    private List<ChatContact> matchesFull;

    @Override
    public Filter getFilter() {
        return matchesFilter;
    }

    private Filter matchesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChatContact> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(matchesFull);
            }

            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ChatContact contact : matchesFull){
                    if (contact.getUsername().toLowerCase().contains(filterPattern)){
                        filteredList.add(contact);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            matches.clear();
            matches.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView username;
        public TextView message;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            username = (TextView) v.findViewById(R.id.firstLine);
            message = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public ChatAdapter(List<ChatContact> list) {
        matches = list;
        matchesFull = new ArrayList<>(list);
    }

    public void add(int position, ChatContact contact) {
        matches.add(position, contact);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        matches.remove(position);
        notifyItemRemoved(position);
    }




    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ChatContact contact = matches.get(position);
        final String name = contact.getUsername();
        final String message = contact.getMessage();

        holder.username.setText(name);
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.message.setText(message);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matches.size();
    }


}
