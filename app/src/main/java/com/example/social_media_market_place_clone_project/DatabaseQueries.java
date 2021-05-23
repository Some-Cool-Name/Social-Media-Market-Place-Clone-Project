package com.example.social_media_market_place_clone_project;

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

}
