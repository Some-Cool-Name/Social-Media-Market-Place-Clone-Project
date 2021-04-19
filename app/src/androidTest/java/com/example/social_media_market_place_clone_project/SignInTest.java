package com.example.social_media_market_place_clone_project;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SignInTest {

    private static String username = "morning";
    private static String password = "m";

    @Rule
    public ActivityScenarioRule<SignIn> activityRule
            = new ActivityScenarioRule<>(SignIn.class);

    @Test
    public void test_login_functionality() {
        onView(withId(R.id.editTextSignInEmailAddress)).perform(typeText(username),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignInPassword)).perform(typeText(password),
                closeSoftKeyboard());
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(allOf(withId(R.id.test_TextView), isDisplayed()));
    }

    @Test
    public void test_login_functionality_with_wrong_login_details() {
        onView(withId(R.id.editTextSignInEmailAddress)).perform(typeText("wrong"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignInPassword)).perform(typeText("password"),
                closeSoftKeyboard());
        onView(withId(R.id.sign_in_button)).perform(click());

        onView(allOf(withId(R.id.editTextSignInEmailAddress), isDisplayed()));
    }

}