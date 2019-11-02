package com.murdoch.ict376.whatsfordinner;

import com.murdoch.ict376.whatsfordinner.database.Recipe;

public class RecipeTest {

    public static boolean isValidName(String str){

        if (str == null || str == "")
            return false;
        else
            return true;
    }

    public static Recipe createRecipe(String name) {
        Recipe recipe = new Recipe(name);
        return recipe;
    }
}
