package com.example.social_media_market_place_clone_project;

import android.app.Activity;
import android.net.Uri;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class ImageHandlerTest {

    private String invalid = "invalid";
    private URI uri;

    @Test
    public void invalidDataIntoCloudinary(){
        ImageHandler imageHandler = new ImageHandler();
        int result = imageHandler.uploadToCloudinary(invalid);
        assertEquals(0, result);
    }

    @Test
    public void invalidDataIntoGetRealPathFromUriMethod(){
        Activity m = new Activity();
        Uri myUri = Uri.parse("http://www.google.com");
        ImageHandler imageHandler = new ImageHandler();
        String result = imageHandler.getRealPathFromUri(myUri, m);
        assertEquals("0", result);
    }

}