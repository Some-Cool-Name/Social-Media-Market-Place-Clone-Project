package com.example.social_media_market_place_clone_project;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ImageHandlerTest {

    private String invalid = "invalid";
    //private String valid = "https://lamp.ms.wits.ac.za/home/s1851427/picture/Hello.jpg";
    private Uri uri=null;
    //private URL url=null;

    private Context context = ApplicationProvider.getApplicationContext();

   @Test
    public void invalidDataIntoCloudinaryOriginal(){
        ImageHandler imageHandler = new ImageHandler(context);
        int result = imageHandler.uploadToCloudinary(invalid);
        assertEquals(0, result);
    }

    @Test
    public void validDataIntoCloudinary(){
        ImageHandler imageHandler = mock(ImageHandler.class);
        when(imageHandler.uploadToCloudinary(anyString())).thenReturn(1);
        int result = imageHandler.uploadToCloudinary(invalid);
        verify(imageHandler).uploadToCloudinary(anyString());
        assertEquals(1, result);
    }
    @Test
    public void invalidDataIntoCloudinary(){
        ImageHandler imageHandler = mock(ImageHandler.class);
        when(imageHandler.uploadToCloudinary(anyString())).thenReturn(0);
        int result = imageHandler.uploadToCloudinary(invalid);
        verify(imageHandler).uploadToCloudinary(anyString());
        assertEquals(0, result);
    }

    @Test
    public void testGetRealPathFromUriFail(){
        MainActivity m = mock(MainActivity.class);
        ImageHandler imageHandler = new ImageHandler(context);
        String result = imageHandler.getRealPathFromUri(uri, m );
        assertEquals("0", result);

    }

    @Test
    public void testGetRealPathFromUriPass() {
        MainActivity m = mock(MainActivity.class);
        ImageHandler imageHandler = mock(ImageHandler.class);
        when(imageHandler.getRealPathFromUri(uri, m)).thenReturn("1");
        String result = imageHandler.getRealPathFromUri(uri, m);
        verify(imageHandler).getRealPathFromUri(uri, m);
        assertEquals("1", result);
    }


}