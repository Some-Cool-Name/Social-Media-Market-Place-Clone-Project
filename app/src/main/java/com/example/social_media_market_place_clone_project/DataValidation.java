package com.example.social_media_market_place_clone_project;

public class DataValidation {
    public String validateSignUp1(String username,String password,String confirmPassword){
        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            return "Fill Out All Fields";
        }
        else if(username.contains(".")||username.contains("#")||username.contains("$")||username.contains("[")||username.contains("]")){
            return "Username can not have special characters";
        }
        else if(!password.equals(confirmPassword)){
            return "Passwords Do Not Match";
        }
        else{
            return "Valid";
        }
    }

    public String validateSignuUp2(String username){
        if(username.contains("0") || username.contains("1") || username.contains("2") ||
                username.contains("3") || username.contains("4") || username.contains("5") ||
                username.contains("6") || username.contains("7") || username.contains("8") ||
                username.contains("9")){
            return "Username Cannot Contain Numbers";
        }else{
            return "Valid";
        }
    }



}
