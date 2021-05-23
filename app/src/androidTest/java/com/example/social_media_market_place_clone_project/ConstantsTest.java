package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;

import org.junit.Test;

public class ConstantsTest extends TestCase {

    @Test
    public void testGetUsername(){
        Constants constants = new Constants();
        String results = constants.usernaam;
        assertEquals(results, "");
    }

    @Test
    public void testGetLiked(){
        Constants constants = new Constants();
        String results = constants.liked;
        assertEquals(results, "");
    }

}