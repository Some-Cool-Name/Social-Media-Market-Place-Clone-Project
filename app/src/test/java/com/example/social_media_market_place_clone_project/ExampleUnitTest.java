package com.example.social_media_market_place_clone_project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calculatorAddTwoPass(){
        Calculator calculator = new Calculator();
        int sum = calculator.addTwo(3, 4);
        assertEquals(sum , 7);
    }
}

