package com.example.social_media_market_place_clone_project;

import com.squareup.picasso.Picasso;

public class MiniProfile {
    // string course_name for storing course_name
    // and imgid for storing image id.
    private String username, userAge;
    private String imageId;



    public MiniProfile(String name, String age, String id) {
        username = name;
        userAge = age;
        imageId = id;



    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

}
