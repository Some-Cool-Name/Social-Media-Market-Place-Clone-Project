package com.example.social_media_market_place_clone_project;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SessionManagerTest {

    private String email = "test@gmail.com",
            fullname = "test user",
            birthday = "10 May 2021",
            gender = "male",
            sexuality = "heterosexual",
            bio = "fun and stuff",
            profilePicture = "https://someurl.com";
    private Context context = ApplicationProvider.getApplicationContext();

    SessionManager sessionManager = new SessionManager(context);

    @Test
    public void getUserDetails(){
        sessionManager.createSession(email, fullname, birthday, gender, sexuality, bio, profilePicture);
        assertEquals(email, sessionManager.getUserDetails().get("EMAIL"));
    }

    @Test
    public void checkLogin(){
        sessionManager.createSession(email, fullname, birthday, gender, sexuality, bio, profilePicture);
        assertEquals(true, sessionManager.checkLogin());
    }

}