package com.example.social_media_market_place_clone_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "LOGIN";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String EMAIL = "EMAIL";
    public static final String FULLNAME = "FULLNAME";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String SEXUALITY = "SEXUALITY";
    public static final String GENDER = "GENDER";

    /*
    everytime a user logs in, we make a request with the email to the database
    and get demographic information, we use that info to create a session so that all the
    info is close by
     */



    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    //create a login session
    public void createSession(String email, String fullname, String birthday, String gender, String sexuality){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(FULLNAME, fullname);
        editor.putString(EMAIL,email);
        editor.putString(BIRTHDAY,birthday);
        editor.putString(GENDER,gender);
        editor.putString(SEXUALITY,sexuality);
        editor.apply();

    }

    //check if user is logged in

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(FULLNAME, sharedPreferences.getString(FULLNAME, null));
        user.put(BIRTHDAY, sharedPreferences.getString(BIRTHDAY, null));
        user.put(SEXUALITY, sharedPreferences.getString(SEXUALITY, null));
        user.put(GENDER, sharedPreferences.getString(GENDER, null));
        return user;
    }
    public void logoutUser(){
        editor.clear();
        editor.commit();
        /*Intent i = new Intent(context, SplashScreen.classs);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        ((settings) context).finish();*/
    }


}
