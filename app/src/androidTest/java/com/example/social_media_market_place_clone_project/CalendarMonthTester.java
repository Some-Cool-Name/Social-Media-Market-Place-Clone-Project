package com.example.social_media_market_place_clone_project;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalendarMonthTester {

    CalendarMonth calendarMonth = new CalendarMonth();

    @Test
    public void getJanTest(){
        String result = calendarMonth.getMonth(1);
        assertEquals(result, "January");
    }

    @Test
    public void getFebTest(){
        String result = calendarMonth.getMonth(2);
        assertEquals(result, "February");
    }

    @Test
    public void getMarchTest(){
        String result = calendarMonth.getMonth(3);
        assertEquals(result, "March");
    }

    @Test
    public void getAprilTest(){
        String result = calendarMonth.getMonth(4);
        assertEquals(result, "April");
    }

    @Test
    public void getMayTest(){
        String result = calendarMonth.getMonth(5);
        assertEquals(result, "May");
    }

    @Test
    public void getJuneTest(){
        String result = calendarMonth.getMonth(6);
        assertEquals(result, "June");
    }

    @Test
    public void getJulyTest(){
        String result = calendarMonth.getMonth(7);
        assertEquals(result, "July");
    }

    @Test
    public void getAugTest(){
        String result = calendarMonth.getMonth(8);
        assertEquals(result, "August");
    }

    @Test
    public void getSeptTest(){
        String result = calendarMonth.getMonth(9);
        assertEquals(result, "September");
    }

    @Test
    public void getOctTest(){
        String result = calendarMonth.getMonth(10);
        assertEquals(result, "October");
    }

    @Test
    public void getNovTest(){
        String result = calendarMonth.getMonth(11);
        assertEquals(result, "November");
    }

    @Test
    public void getDecTest(){
        String result = calendarMonth.getMonth(12);
        assertEquals(result, "December");
    }

}
