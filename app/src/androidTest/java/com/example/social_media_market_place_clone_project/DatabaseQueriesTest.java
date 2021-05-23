package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabaseQueriesTest {

    @Test
    public void invalidLoginTest(){
        DatabaseQueries loginTest = new DatabaseQueries();
        String results = loginTest.loginUser("demo", "12344566");
        assertEquals(results, "{\"login\":[],\"success\":\"0\",\"message\":\"Wrong Password\"}");

    }

}