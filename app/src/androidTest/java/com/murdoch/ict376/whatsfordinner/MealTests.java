package com.murdoch.ict376.whatsfordinner;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MealTests {

    @Test
    public void nameValidator_isCorrectName() {
        assertTrue(MealTest.isValidName("Beef Pie"));

    }

    @Test
    public void nameValidator_isEmptyString() {
        assertFalse(MealTest.isValidName(""));

    }

    @Test
    public void nameValidator_isNull() {
        assertFalse(MealTest.isValidName(null));

    }
}
