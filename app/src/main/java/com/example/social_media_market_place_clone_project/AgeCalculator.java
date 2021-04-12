package com.example.social_media_market_place_clone_project;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class AgeCalculator {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateAge(String birthDate){
        // Convert birthdayString to Integers //
        int bYear = Integer.parseInt(String.valueOf(birthDate.charAt(6)))*1000
                +Integer.parseInt(String.valueOf(birthDate.charAt(7)))*100
                +Integer.parseInt(String.valueOf(birthDate.charAt(8)))*10
                +Integer.parseInt(String.valueOf(birthDate.charAt(9)));
        int bMonth = Integer.parseInt(String.valueOf(birthDate.charAt(3)))*10
                + Integer.parseInt(String.valueOf(birthDate.charAt(4)));
        int bDay = Integer.parseInt(String.valueOf(birthDate.charAt(0)))*10
                +Integer.parseInt(String.valueOf(birthDate.charAt(1)));

       /* LocalDate birthday= LocalDate.of(bYear,bMonth,bDay);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(birthday, now);

        */

        return Integer.toString(2021-bYear);
    }

}
