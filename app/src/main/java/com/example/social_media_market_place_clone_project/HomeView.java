package com.example.social_media_market_place_clone_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.HttpUrl;

public class HomeView extends AppCompatActivity {
    ImageView imageView;
    TextView nameAge, location;
    ImageButton cross;
    ArrayList<User> users = new ArrayList<>();
    int index = 0;

    private SwipeDeck cardStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        // Text Views for the Name, Age and location
        //nameAge = (TextView) findViewById(R.id.home_name_text);
        //location = (TextView) findViewById(R.id.home_location_text);
        cross = findViewById(R.id.cross);
        imageView = findViewById(R.id.picture);

        // Instructions
        String instructions = "Swipe Left for No" + "\n" + "Swipe Right for Yes" + "\n" + "\n" + "Information Displayed:" + "\n" + " • Name" + "\n" + " • Interest" + "\n" + " • Bio";
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeView.this);
        builder.setTitle("Instructions");
        builder.setMessage(instructions);

        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

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
                // on card swipped to right we are displaying a toast message.
                //Toast.makeText(HomeView.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
                //send the liked request

                ;
                Constants constants = new Constants();

                SessionManager sessionManager = new SessionManager(HomeView.this);
                sessionManager.checkLogin();
                HashMap<String, String> currentUser = sessionManager.getUserDetails();

                // Display Name and Age
                String n = currentUser.get("EMAIL");
                backGroundLike(n, users.get(position).getEmail());
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(HomeView.this, "No more users present", Toast.LENGTH_SHORT).show();
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

    public void like(String username, String liked) {

        AsyncNetwork request = new AsyncNetwork();
        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDALikeUser.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("likerUsername", username);
        urlBuilder.addQueryParameter("likeeUsername", liked);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            System.out.print("loading");
        }


        // Request is finished

    }

    private void backGroundLike(String username, String like) {
        runOnUiThread(new Runnable() {
            public void run() {
                like(username, like);
            }
        });
    }

    public void dislike(String username, String disliked) throws JSONException {
        AsyncNetwork request = new AsyncNetwork();

        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDARejectUser.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("rejectorUsername", username);
        urlBuilder.addQueryParameter("rejecteeUsername", disliked);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            System.out.print("loading");
        }


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
        ArrayList<User> users = new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();
        SessionManager sessionManager = new SessionManager(HomeView.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();


        // Display Name and Age
        String n = currentUser.get("EMAIL");

        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAgetFeed.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username", n);
        String url = urlBuilder.build().toString();
        request.execute(url);


        while (request.Result.equals("Waiting")) {
            System.out.print("waiting");
            // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

        }


        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("feedProfiles").toString()); // extract the login credentials array

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userCredentials = jsonArray.getJSONObject(i);
            User newUser = new User();
            newUser.setName(userCredentials.getString("Full_Name"));
            newUser.setEmail(userCredentials.getString("E_mail"));
            newUser.setBio(userCredentials.getString("Bio"));
            newUser.setImageUrl(userCredentials.getString("Profile_Picture"));
            users.add(newUser);

        }

        return users;
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

/*try {
            users = getUsers();
            if (users != null) {
                nameAge.setText(users.get(index).getName());
                String url = users.get(index).getImageUrl();
                loadImageFromUrl(url);
                index++;
            }

            cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameAge.setText(users.get(index).getName());
                    String url = users.get(index).getImageUrl();
                    loadImageFromUrl(url);
                    if (index < users.size() - 1) {
                        index++;
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    /*
    private void loadImageFromUrl(String url) {

        Picasso.with(this).load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }*/
}