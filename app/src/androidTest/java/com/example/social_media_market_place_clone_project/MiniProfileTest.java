package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;

import org.junit.Test;

public class MiniProfileTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testTestGetUsername(){
        MiniProfile miniProfile = new MiniProfile("Thando", "15", 15);
        miniProfile.setUsername("thando@gmail.com");
        String results = miniProfile.getUsername();
        assertEquals(results, "thando@gmail.com");
    }

    @Test
    public void testTestGetUserAge(){
        MiniProfile miniProfile = new MiniProfile("Thando", "15", 15);
        miniProfile.setUserAge("15");
        String results = miniProfile.getUserAge();
        assertEquals(results, "15");
    }

    @Test
    public void testTestGetImageID(){
        MiniProfile miniProfile = new MiniProfile("Thando", "15", 15);
        miniProfile.setImageId(15);
        int results = miniProfile.getImageId();
        assertEquals(results, 15);
    }

}