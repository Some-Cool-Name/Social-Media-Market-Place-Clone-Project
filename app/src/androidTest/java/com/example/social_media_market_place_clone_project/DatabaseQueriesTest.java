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

    //@Test
    /*public void validLoginTest(){// from signup
        DatabaseQueries loginTest = new DatabaseQueries();
        String results = loginTest.loginUser("demo", "demo");
        assertEquals(results, "{\"login\":[{\"username\":\"demo\",\"name\":\"demo\",\"Birthday\":\"10-05-1993\",\"gender\":\"Male\",\"Sexuality\":\"Female\",\"bio\":\"demo\",\"profile_picture\":\"https:\\/\\/res.cloudinary.com\\/dkctv74ue\\/image\\/upload\\/v1620629571\\/fcanjezu1uikmbwf58ta.jpg\"}],\"success\":\"1\",\"message\":\"success\"}");
    }*/

    @Test
    public void validGetFeedTest(){ // from home view
        DatabaseQueries getFeedTest = new DatabaseQueries();
        ArrayList<User> results = getFeedTest.getUsersFeed("demo");
        assertThat(results.isEmpty(), is(false));
    }
    @Test
    public void successUpdateNameTest(){ // from edit profile
        DatabaseQueries updateName = new DatabaseQueries();
        String results = updateName.doUpdateName("aj","aj");
        assertEquals(results, "{\"success\":\"1\",\"message\":\"success\"}");
    }

    @Test
    public void successUpdateBioTest(){ // from edit profile
        DatabaseQueries updateBio = new DatabaseQueries();
        String results = updateBio.doUpdateBio("aj","No hook ups.");
        assertEquals(results, "{\"success\":\"1\",\"message\":\"success\"}");
    }
    @Test
    public void successUpdateProfileTest(){ // from edit profile
        DatabaseQueries updateProfile = new DatabaseQueries();
        String results = updateProfile.doUpdateProfile("aj","https://res.cloudinary.com/dkctv74ue/image/upload/v1621747820/h1ptx4syasnsytnwoxm8.jpg");
        assertEquals(results, "{\"success\":\"1\",\"message\":\"success\"}");
    }

}