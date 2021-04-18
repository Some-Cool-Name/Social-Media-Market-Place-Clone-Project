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

public class SignUp2Test {

    private static String birthday = "12 April 2020";
    private static String name = "some name";
    private static String bio = "having fun or something";

    @Rule
    public ActivityScenarioRule<SignUp2> activityRule
            = new ActivityScenarioRule<>(SignUp2.class);

    @Test
    public void test_sign_up_2_functionality() {
        onView(withId(R.id.sign_up_birthday_text2)).perform(typeText(birthday),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignUpName)).perform(typeText(name),
                closeSoftKeyboard());
        onView(withId(R.id.editTextSignUpBio)).perform(typeText(bio),
                closeSoftKeyboard());
        onView(withId(R.id.sign_up_next_button)).perform(click());

        onView(allOf(withId(R.id.sign_up_register_button), isDisplayed()));
    }

}