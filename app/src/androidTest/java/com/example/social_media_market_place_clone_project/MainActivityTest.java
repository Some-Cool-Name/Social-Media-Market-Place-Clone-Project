package com.example.social_media_market_place_clone_project;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<Settings> mActivityTestRule = new ActivityTestRule<>(Settings.class);

    @Test
    public void settingsActivityLayoutTest() {
        /*
        added sleep to delay the app
        */
        try{
            Thread.sleep(15);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        onView(withId(R.id.setting_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void settingsActivityViewsTest() {
        /*
        added sleep to delay the app
        */
        try{
            Thread.sleep(15);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.security_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.theme_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.help_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.logout_layout)).check(matches(isDisplayed()));

    }

    @Test
    public void settingsActivityPressTest() {
        /*
        added sleep to delay the app
        */
        try{
            Thread.sleep(15);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        onView(withId(R.id.edit_layout)).perform(click());
        onView(withId(R.id.editTextEditProfileName)).check(matches(isDisplayed()));

    }
}
