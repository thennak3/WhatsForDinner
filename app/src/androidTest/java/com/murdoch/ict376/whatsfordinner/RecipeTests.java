package com.murdoch.ict376.whatsfordinner;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecipeTests {

    @Test
    public void nameValidator_isCorrectName() {
        assertTrue(RecipeTest.isValidName("Beef Pie"));

    }

    @Test
    public void nameValidator_isEmptyString() {
        assertFalse(RecipeTest.isValidName(""));

    }

    @Test
    public void nameValidator_isNull() {
        assertFalse(RecipeTest.isValidName(null));

    }
}
