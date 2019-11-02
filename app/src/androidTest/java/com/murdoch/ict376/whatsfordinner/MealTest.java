package com.murdoch.ict376.whatsfordinner;

import com.murdoch.ict376.whatsfordinner.database.Meal;
import java.util.Date;
import java.util.regex.Pattern;

public class MealTest {

    public static Meal createMeal(int id, Date date) {
        Meal meal = new Meal(id, date);
        return meal;
    }

    public static Meal updateMeal(Meal meal, int recipeID) {

        meal.setRecipeID(recipeID);

        return meal;
    }

    public static boolean isValidID(int id){

        if (id == (int)id){
            if (id >= 0)
                return true;
        }

        return false;
    }

}
