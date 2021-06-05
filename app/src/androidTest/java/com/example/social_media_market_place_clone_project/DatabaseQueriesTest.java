package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DatabaseQueriesTest {

    @Test
    public void invalidLoginTest(){// from signup
        DatabaseQueries loginTest = new DatabaseQueries();
        String results = loginTest.loginUser("demo", "12344566");
        loginTest.doLikeFeed("demo", "demo demo");
        loginTest.doDislikeFeed("demo", "aj");
        assertEquals(results, "{\"login\":[],\"success\":\"0\",\"message\":\"Wrong Password\"}");
    }

    @Test
    public void validGetFeedTest(){
        DatabaseQueries getFeedTest = new DatabaseQueries();
        ArrayList<User> results = getFeedTest.getUsersFeed("demo");
        assertThat(results.isEmpty(), is(false));
    }


}