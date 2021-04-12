package com.example.social_media_market_place_clone_project;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

public class SignUpTest {

    private static String username = "test";
    private static String password = "pass";

    @Rule
    public ActivityScenarioRule<SignUp> activityRule
            = new ActivityScenarioRule<>(SignUp.class);

    @Test
    public void test_sign_up_functionality() {
        onView(withId(R.id.editTextSignUpEmail)).perform(typeText(username),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignUpPassword)).perform(typeText(password),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignUpConfirmPassword)).perform(typeText(password),
                closeSoftKeyboard());
        onView(withId(R.id.sign_up_next_button)).perform(click());

        onView(allOf(withId(R.id.sign_up_profile_picture_text), isDisplayed()));
    }

}