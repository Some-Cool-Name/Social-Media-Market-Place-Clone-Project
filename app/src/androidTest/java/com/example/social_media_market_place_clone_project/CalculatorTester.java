package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTester {
    @Test
    public void calculatorAddTwoPass(){
        Calculator calculator = new Calculator();
        int sum = calculator.addTwo(3, 4);
        assertEquals(sum , 7);
    }
}