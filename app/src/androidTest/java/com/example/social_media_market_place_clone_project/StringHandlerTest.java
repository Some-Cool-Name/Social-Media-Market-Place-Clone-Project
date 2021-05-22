package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringHandlerTest {

    StringHandler stringHandler = new StringHandler();

    @Test
    public void addCharToString(){
        String result = stringHandler.addChar("sucess", 'c', 2);
        assertEquals("success", result);
    }

    @Test
    public void getDateString(){
        String result = stringHandler.getDateString(10, "May", 2021);
        assertEquals("10 May 2021", result);
    }

}