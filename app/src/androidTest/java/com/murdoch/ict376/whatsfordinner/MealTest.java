package com.murdoch.ict376.whatsfordinner;

import com.murdoch.ict376.whatsfordinner.database.Meal;
import java.util.Date;

public class MealTest {

    public static boolean isValidName(String str){

        if (str == null || str == "")
            return false;
        else
            return true;
    }

    public static Meal createMeal(int id, Date date) {
        Meal meal = new Meal(id, date);
        return meal;
    }
}
