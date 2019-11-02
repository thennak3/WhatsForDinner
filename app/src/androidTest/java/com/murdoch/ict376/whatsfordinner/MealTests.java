package com.murdoch.ict376.whatsfordinner;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MealTests {

    @Test
    public void nameValidator_isCorrectID() {
        assertTrue(MealTest.isValidID(2));

    }

    @Test
    public void nameValidator_isNegative() {
        assertFalse(MealTest.isValidID(-3));

    }
}
