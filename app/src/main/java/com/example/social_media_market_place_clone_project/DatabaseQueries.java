package com.example.social_media_market_place_clone_project;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.HttpUrl;

public class DatabaseQueries {

    public String loginUser (String username, String password){
        AsyncNetwork request = new AsyncNetwork();

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAGet.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",username);
        urlBuilder.addQueryParameter("password",password);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            // Toast.makeText(SignIn.this,"Loading",Toast.LENGTH_SHORT).show();
            System.out.print("loading");
        }

        return request.Result;
    }

    public void doLikeFeed(String username, String liked){
        AsyncNetwork request = new AsyncNetwork();
        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDALikeUser.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("likerUsername",username);
        urlBuilder.addQueryParameter("likeeUsername",liked);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            System.out.print("loading");
        }

    }
    public void doDislikeFeed(String username, String disliked){

        AsyncNetwork request = new AsyncNetwork();

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDARejectUser.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("rejectorUsername",username);
        urlBuilder.addQueryParameter("rejecteeUsername",disliked);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            System.out.print("loading");
        }


    }

    public ArrayList<User> getUsersFeed(String n){

        ArrayList<User> users = new ArrayList<>();
        AsyncNetwork request = new AsyncNetwork();

        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAgetFeed.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",n);
        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            System.out.print("waiting");
            // Toast.makeText(HomeView.this,"",Toast.LENGTH_SHORT).show();
        }

        // Request is finished
        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }

        return  users;
    }


    public String doUpdateName(String username, String name){
        AsyncNetwork request = new AsyncNetwork();
        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpName.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("name",name);
        urlBuilder.addQueryParameter("username",username);

        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
           // Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
        }

        return request.Result;

    }

    public String doUpdateBio(String username, String biography){
        AsyncNetwork request = new AsyncNetwork();
        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpBio.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("biography", biography);
        urlBuilder.addQueryParameter("username",username);

        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
          //  Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
        }

        return request.Result;

    }

    public String doUpdateProfile(String username, String updatedImageUrl){
        AsyncNetwork request = new AsyncNetwork();
        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpPicture.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",username);
        urlBuilder.addQueryParameter("profile_picture",updatedImageUrl);

        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            //Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
        }

        return request.Result;

    }

}
