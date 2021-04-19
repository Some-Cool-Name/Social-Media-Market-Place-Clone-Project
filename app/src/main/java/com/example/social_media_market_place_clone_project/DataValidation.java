package com.example.social_media_market_place_clone_project;

public class DataValidation {
    public String validateSignUp1(String email,String password,String confirmPassword){
        if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            return "Fill Out All Fields";
        }else if(!password.equals(confirmPassword)){
            return "Passwords Do Not Match";
        }
        else{
            return "Valid";
        }
    }

    public String validateSignUp2(String username,String birthday){
        if(username.contains("0") || username.contains("1") || username.contains("2") ||
                username.contains("3") || username.contains("4") || username.contains("5") ||
                username.contains("6") || username.contains("7") || username.contains("8") ||
                username.contains("9")){
                return "Username Cannot Contain Numbers";
        }else{
           if(Integer.parseInt(birthday)<=17){
               return "Only Users Over 18 May Register";
           }else{
               return "Valid";
           }
        }
    }

}
