package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    @Test
    public void testTestGetEmail() {
        User user = new User();
        user.setEmail("thando@gmail.com");
        String results = user.getEmail();
        assertEquals(results, "thando@gmail.com");

    }

    @Test
    public void testTestGetName() {
        User user = new User();
        user.setName("Thando");
        String results = user.getName();
        assertEquals(results, "Thando");

    }

    @Test
    public void testTestGetGender() {
        User user = new User();
        user.setGender("Female");
        String results = user.getGender();
        assertEquals(results, "Female");

    }

    @Test
    public void testTestGetBio() {
        User user = new User();
        user.setBio("This is a bio");
        String results = user.getBio();
        assertEquals(results, "This is a bio");

    }

    @Test
    public void testTestGetImageUrl() {
        User user = new User();
        user.setImageUrl("www.google.com");
        String results = user.getImageUrl();
        assertEquals(results, "www.google.com");

    }

}