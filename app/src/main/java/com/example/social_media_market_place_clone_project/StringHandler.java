package com.example.social_media_market_place_clone_project;

public class StringHandler {

    public String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    public String getDateString(int day, String monthString, int year){
        return day + " " + monthString + " " + year;
    }

}
