package com.murdoch.ict376.whatsfordinner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    long insert(Recipe recipe);

    @Query("SELECT * FROM RECIPE WHERE RecipeID = :id LIMIT 1")
    Recipe findRecipeByID(int id);

    @Query("DELETE FROM RECIPE WHERE RecipeID = :id")
    void deleteByID(int id);

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT * FROM RECIPE ORDER BY NAME")
    LiveData<List<Recipe>> getAllRecipes();


}
