package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationTester {
    @Test
    public void usernameContainsNumber(){
      DataValidation dataValidation = new DataValidation();
      String result = dataValidation.validateSignuUp2("1one");
      assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameNotContainsNumber(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("one");
        assertEquals("Valid", result);
    }
    @Test
    public void emptyFields(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("one@gmail.com", "", "1234");
        assertEquals("Fill Out All Fields", result);
    }
    @Test
    public void passwordDifferent(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("one@gmail.com", "123", "1234");
        assertEquals("Passwords Do Not Match", result);
    }
    @Test
    public void allValid(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("one@gmail.com", "1234", "1234");
        assertEquals("Valid", result);
    }
}

