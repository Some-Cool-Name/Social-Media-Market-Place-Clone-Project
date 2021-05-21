package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AgeCalculatorTester {
    AgeCalculator ageCalculator = new AgeCalculator();

    @Test
    public void calculateAgeTest(){
        String age = ageCalculator.calculateAge("31-03-2000");

        assertEquals(age,"21");

    }
}
