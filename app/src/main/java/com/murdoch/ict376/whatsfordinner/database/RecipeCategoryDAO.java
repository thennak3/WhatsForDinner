package com.murdoch.ict376.whatsfordinner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeCategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeCategory recipeCategory);

    @Query("select Category.categoryID, name from Category inner join RecipeCategory on category.categoryID = RecipeCategory.CategoryID where RecipeCategory.RecipeID=:recipeID")
    List<Category> getCategoriesForRecipe(int recipeID);

    @Query("select Recipe.RecipeID, Name, Picture, PreparationTime, CookingTime, WebsiteURL, LetsEatRecipeUUID, UserAdded, DateModified  from Recipe inner join RecipeCategory on Recipe.RecipeID = RecipeCategory.RecipeID where RecipeCategory.CategoryID=:categoryID")
    List<Recipe> getRecipesForCategory(int categoryID);

    @Query("select * from RecipeCategory where recipeID=:id")
    List<RecipeCategory> getRecipeCategoriesByRecipeID(int id);

    @Query("select * from RecipeCategory where categoryID=:id")
    List<RecipeCategory> getRecipeCategoriesByCategoryID(int id);

    @Query("DELETE FROM RECIPECATEGORY where recipeID=:id")
    void delete(Long id);

    @Delete
    void delete(RecipeCategory... recipeCategories);

    @Query("DELETE FROM RecipeCategory")
    void deleteAll();

}
