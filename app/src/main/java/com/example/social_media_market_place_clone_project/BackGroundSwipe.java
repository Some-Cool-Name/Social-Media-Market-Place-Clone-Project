package com.example.social_media_market_place_clone_project;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.HttpUrl;

public class BackGroundSwipe extends AsyncTask<String , Integer, String> {
    String Result= "Waiting";

    @Override
    protected String doInBackground(String ...names) {

        Constants constants = new Constants();
//        if(names.equals("like")){
//            like("vhugala","ty");
//        }
//        else {
//            try {
//                dislike(constants.usernaam, constants.liked);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        like("vhugala","ty");

        constants.usernaam ="";
        constants.liked="";
        Result ="Done";
        return "";
    }

    public void like(String username, String liked){

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


        // Request is finished

    }
    public void dislike(String username, String disliked) throws JSONException {
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


        // Request is finished



    }


}
