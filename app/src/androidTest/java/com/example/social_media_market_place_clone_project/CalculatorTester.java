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
<<<<<<< HEAD

    @Test
    public void calculatorMultiply(){
        Calculator calculator = new Calculator();
        int product = calculator.multiply(3, 2);
        assertEquals(product, 6);
    }
=======
>>>>>>> 7fa9cb371e38b5fd06a5b7a35ea403203319b1c3
}
