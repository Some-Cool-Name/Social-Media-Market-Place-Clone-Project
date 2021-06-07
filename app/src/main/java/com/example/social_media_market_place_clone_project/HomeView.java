package com.example.social_media_market_place_clone_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.HashMap;

import okhttp3.HttpUrl;

public class HomeView extends AppCompatActivity {
    ImageView imageView;
    TextView nameAge, location;
    ImageButton cross, search;
    EditText searchText;
    ArrayList<User> users = new ArrayList<>();
    String lat;
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
        search = findViewById(R.id.feed_search_Button);
        searchText = findViewById(R.id.feed_search_EditText);
        // Instructions
     /*   String instructions = "Swipe Left for No" + "\n" + "Swipe Right for Yes" + "\n" + "\n" + "Information Displayed:" + "\n" + " • Name" + "\n" + " • Interest" + "\n" + " • Bio";
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeView.this);
        builder.setTitle("Instructions");
        builder.setMessage(instructions);

        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();*/

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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<User> newAl = new ArrayList<>();
                //newAl = al;

                //newAl.removeIf(s -> !s.contains(searchText.getText().toString()));
                if(!searchText.getText().toString().equals("")) {
                    for (int i = 0; i < users.size(); i++) {

                        if (users.get(i).getInterests().contains(searchText.getText().toString())  || users.get(i).getLocation().contains(searchText.getText().toString())) {
                            newAl.add(users.get(i));
                        }
                    }

                    //al.add("second");
                    final HomeViewCardAdapter adapter = new HomeViewCardAdapter(newAl, HomeView.this);
                    cardStack.setAdapter((Adapter) adapter);
                }else {
                    final HomeViewCardAdapter adapter = new HomeViewCardAdapter(users, HomeView.this);
                    cardStack.setAdapter((Adapter) adapter);
                }
            }
        });
        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.

                //send disliked request

                SessionManager sessionManager = new SessionManager(HomeView.this);
                sessionManager.checkLogin();
                HashMap<String, String> currentUser = sessionManager.getUserDetails();

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
        startActivity(intent);
    }

    public void Profile(View v) {
        Intent intentSignIn = new Intent(HomeView.this, Profile.class);
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
        String loc = currentUser.get("LOCATION");
        String [] splitted = splitString(loc);
        Double mylat = Double.parseDouble(splitted[0]);
        Double mylong = Double.parseDouble(splitted[1]);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userCredentials = jsonArray.getJSONObject(i);
            ArrayList<String> interests = new ArrayList<>();
            User newUser = new User();

            ArrayList<Double> theirs = new ArrayList<>();
            // when we create a user add distance from the user to the user in feed
            newUser.setName(userCredentials.getString("Full_Name"));
            newUser.setEmail(userCredentials.getString("E_mail"));
            newUser.setBio(userCredentials.getString("Bio"));
            String locationAll = userCredentials.getString("Location");
            String [] splittedTheirs = splitString(locationAll);
            Double tlat = Double.parseDouble(splittedTheirs[0]);
            Double tlong = Double.parseDouble(splittedTheirs[1]);


            newUser.setDistanceFromUser(mylat,tlat,mylong,tlong);
            newUser.setImageUrl(userCredentials.getString("Profile_Picture"));
            String locationName="";
            for (int j=2; j < splittedTheirs.length; j ++){

                if(j==2){
                    locationName = locationName + splittedTheirs[j];
                }
                else {
                    locationName = locationName + " " + splittedTheirs[j];
                }
            }
            newUser.setLocation(locationName);
            interests.add(userCredentials.getString("Interest_1").toLowerCase());
            interests.add(userCredentials.getString("Interest_2").toLowerCase());
            interests.add(userCredentials.getString("Interest_3").toLowerCase());
            interests.add(userCredentials.getString("Interest_4").toLowerCase());
            interests.add(userCredentials.getString("Interest_5").toLowerCase());
            newUser.setInterests(interests);
            users.add(newUser);

        }
        //quickSort(users,0,users.size()-1);

        Quicksort quicksort = new Quicksort();
        return quicksort.convert(users);

    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    public ArrayList<Double> getCoordinates(String location) throws JSONException {
        ArrayList<Double>coordinates= new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();
        SessionManager sessionManager = new SessionManager(HomeView.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();


        // Display Name and Age
        String n = currentUser.get("EMAIL");

        String link = "https://api.opencagedata.com/geocode/v1/json";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("key", "9f5b92750f8e4c0e9bdea773974c5f74");
        urlBuilder.addQueryParameter("q",location);

        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            System.out.print("waiting");
            // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();

        }
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("results").toString()); // extract the login credentials array
        //JSONArray jsonArray1 = new JSONArray(jsonArray.getJSONArray(0).toString());
        JSONObject results = jsonArray.getJSONObject(0);
        JSONObject bounds = results.getJSONObject("bounds");
        JSONObject northEast = bounds.getJSONObject("northeast");
        String latitude = northEast.getString("lat");
        String longitude = northEast.getString("lng");

        Double l = Double.parseDouble(latitude);
        Double ln = Double.parseDouble(longitude);
        coordinates.add(l);
        coordinates.add(ln);

        return coordinates;
    }

    public  String [] splitString (String loc){
        return loc.split(" ");

    }

}