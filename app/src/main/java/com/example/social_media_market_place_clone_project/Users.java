package com.example.social_media_market_place_clone_project;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;

public class Users extends AppCompatActivity {
    ListView usersList;
    //TextView noUsersText;
    EditText searchText;
    ImageButton  search;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    ArrayList<String> matches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        usersList = (ListView)findViewById(R.id.usersList);
        //noUsersText = (TextView)findViewById(R.id.noUsersText);
        search = findViewById(R.id.match_search_Button);
        searchText = findViewById(R.id.match_search_EditText);
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();
        loginChat();
        String url = "https://datingapp-d1e37-default-rtdb.firebaseio.com/.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    doOnSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> newAl = new ArrayList<>();
                //newAl = al;

                //newAl.removeIf(s -> !s.contains(searchText.getText().toString()));

                for(int i=0; i< al.size(); i ++){

                    if(al.get(i).contains(searchText.getText().toString())){
                        newAl.add(al.get(i));
                    }
                }

                //al.add("second");
                usersList.setAdapter(new ArrayAdapter<String>(Users.this, android.R.layout.simple_list_item_1, newAl));

            }
        });
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }

    public void doOnSuccess(String s) throws JSONException {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            matches = getMatch();
            totalUsers = matches.size();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(matches.size() <1){
           // noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            //noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            al = matches;
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
        }

        pd.dismiss();
    }

    public ArrayList<String>  getMatch() throws JSONException {
        SessionManager sessionManager = new SessionManager(Users.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();
        // Display Name and Age
        String n = currentUser.get("EMAIL");
        ArrayList<String> users = new ArrayList<>();
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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userCredentials = jsonArray.getJSONObject(i);

            users.add(userCredentials.getString("E_mail"));

        }

        return users;


    }
    public void loginChat(){
        String url = "https://datingapp-d1e37-default-rtdb.firebaseio.com/.json";
        final ProgressDialog pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();
        SessionManager sessionManager = new SessionManager(Users.this);
        sessionManager.checkLogin();
        HashMap<String, String> currentUser = sessionManager.getUserDetails();


        // Display Name and Age
        String n = currentUser.get("EMAIL");

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            if (response.equals("null")) {
                Toast.makeText(Users.this, "user not found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(response);

                    if (!obj.has(n)) {
                        Toast.makeText(Users.this, "user not found", Toast.LENGTH_LONG).show();

                    } else if (obj.getJSONObject(n).getString("password").equals("pass123")) {
                        UserDetails.username = n;
                        UserDetails.password = "pass123";
                        // startActivity(new Intent(SignIn.this, Users.class));
                    } else {
                        Toast.makeText(Users.this, "incorrect password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            pd.dismiss();
        }, volleyError -> {
            System.out.println("" + volleyError);
            pd.dismiss();
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);
    }
    public void Home(View v){
        Intent intentSignIn = new Intent(Users.this, HomeView.class);
        intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSignIn);
    }

    public void Profile(View v){
        Intent intent = new Intent(Users.this, Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Matches(View v){
        Intent intent = new Intent(Users.this, Matches.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}