package com.example.social_media_market_place_clone_project;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_sign_in_button_click() {
        onView(withId(R.id.sign_in_button)).perform(click());
        onView(allOf(withId(R.id.sign_in_background), isDisplayed()));
    }

    @Test
    public void test_sign_up_button_click() {
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(allOf(withId(R.id.create_acc_background), isDisplayed()));
    }

}