package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationTester {
    @Test
    public void usernameContainsZero(){
      DataValidation dataValidation = new DataValidation();
      String result = dataValidation.validateSignuUp2("0one");
      assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsOne(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("1one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsTwo(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("2one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsThree(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("3one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsFour(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("4one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsFive(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("5one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsSix(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("6one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsSeven(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("7one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsEight(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("8one");
        assertEquals("Username Cannot Contain Numbers", result);
    }
    @Test
    public void usernameContainsNine(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("9one");
        assertEquals("Username Cannot Contain Numbers", result);
    }

    @Test
    public void usernameNotContainsNumber(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignuUp2("one");
        assertEquals("Valid", result);
    }

    @Test
    public void emptyPassword(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("one@gmail.com", "", "1234");
        assertEquals("Fill Out All Fields", result);
    }
    @Test
    public void emptyEmail(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("", "1234", "1234");
        assertEquals("Fill Out All Fields", result);
    }
    @Test
    public void emptyConfirmPassword(){
        DataValidation dataValidation = new DataValidation();
        String result = dataValidation.validateSignUp1("one@gmail.com", "1234", "");
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

