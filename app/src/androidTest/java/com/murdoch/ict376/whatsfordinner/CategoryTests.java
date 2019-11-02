package com.murdoch.ict376.whatsfordinner;

import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryTests {

    @Test
    public void nameValidator_isCorrectName() {
        assertTrue(CategoryTest.isValidName("Chicken"));

    }

    @Test
    public void nameValidator_isEmptyString() {
        assertFalse(CategoryTest.isValidName(""));

    }

    @Test
    public void nameValidator_isNull() {
        assertFalse(CategoryTest.isValidName(null));

    }
}
