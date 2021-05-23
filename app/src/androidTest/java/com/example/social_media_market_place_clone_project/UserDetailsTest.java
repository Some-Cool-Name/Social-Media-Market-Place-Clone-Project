package com.example.social_media_market_place_clone_project;

import junit.framework.TestCase;

import org.junit.Test;

public class UserDetailsTest extends TestCase {

    @Test
    public void testUsername(){
        /*
        added sleep to delay the app
        */
        try{
            Thread.sleep(15);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        UserDetails userDetails = new UserDetails();
        String results = userDetails.username;
        assertEquals(results, "");
    }

    @Test
    public void testPassword(){
        UserDetails userDetails = new UserDetails();
        String results = userDetails.password;
        assertEquals(results, "");
    }
    @Test
    public void testChatWith(){
        UserDetails userDetails = new UserDetails();
        String results = userDetails.chatWith;
        assertEquals(results, "");
    }

}