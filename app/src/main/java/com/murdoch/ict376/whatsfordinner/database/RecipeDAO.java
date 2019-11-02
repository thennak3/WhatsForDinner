package com.murdoch.ict376.whatsfordinner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    long insert(Recipe recipe);

    @Query("SELECT * FROM RECIPE WHERE RecipeID = :id LIMIT 1")
    Recipe findRecipeByID(int id);

    @Query("DELETE FROM RECIPE WHERE RecipeID = :id")
    void deleteByID(int id);

    @Delete
    void delete(Recipe...recipes);

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Update
    void updateRecipe(Recipe recipe);

    @Query("Update RECIPE SET name=:new_name WHERE RecipeID=:id")
    void updateByName(int id, String new_name);

    @Query("SELECT * FROM RECIPE ORDER BY NAME")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM RECIPE WHERE RecipeID=:input")
    LiveData<Recipe> getRecipe(Integer input);

}
