package com.murdoch.ict376.whatsfordinner;

import com.murdoch.ict376.whatsfordinner.database.Category;

public class CategoryTest {

    public static boolean isValidName(String str){

        if (str == null || str == "")
            return false;
        else
            return true;
    }

    public static Category createCategory(String name) {
        Category category = new Category(name);
        return category;
    }

    public static Category updateCategory(Category category, String name) {

        category.setName(name);

        return category;
    }
}
