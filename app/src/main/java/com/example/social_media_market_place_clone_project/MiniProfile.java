package com.example.social_media_market_place_clone_project;

public class MiniProfile {
    // string course_name for storing course_name
    // and imgid for storing image id.
    private String username, userAge;
    private int imageId;

    public MiniProfile(String name, String age, int id) {
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
