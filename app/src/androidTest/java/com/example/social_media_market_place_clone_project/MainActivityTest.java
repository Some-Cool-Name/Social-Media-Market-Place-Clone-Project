package com.example.social_media_market_place_clone_project;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void doSignUp() {
        onView(withId(R.id.sign_up_button)).perform(click());\
        onView(withId(R.layout.activity_sign_up)).check(matches(isDisplayed()));
    }

//    @Test
//    public void doSignIn() {
//    }
//
//    @Test
//    public void goToProfile() {
//    }
}