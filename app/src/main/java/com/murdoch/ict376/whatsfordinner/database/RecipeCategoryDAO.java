package com.murdoch.ict376.whatsfordinner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeCategoryDAO {

    @Insert
    void insert(RecipeCategory recipeCategory);

    @Query("select Category.categoryID, name from Category inner join RecipeCategory on category.categoryID = RecipeCategory.CategoryID where RecipeCategory.RecipeID=:recipeID")
    LiveData<List<Category>> getCategoriesForRecipe(int recipeID);

    @Query("select Recipe.RecipeID, Name, Picture, PreparationTime, CookingTime, WebsiteURL, LetsEatRecipeUUID, UserAdded, DateModified  from Recipe inner join RecipeCategory on Recipe.RecipeID = RecipeCategory.RecipeID where RecipeCategory.CategoryID=:categoryID")
    LiveData<List<Recipe>> getRecipesForCategory(int categoryID);

    @Delete
    void Delete(RecipeCategory... recipeCategories);

}
