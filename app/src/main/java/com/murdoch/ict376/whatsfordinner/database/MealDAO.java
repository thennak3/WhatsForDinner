package com.murdoch.ict376.whatsfordinner.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM MEAL WHERE MealDate = :date LIMIT 1")
    Meal findMealByDate(int date);

    /* not sure how to just search for a months worth here
    @Query("SELECT * FROM MEAL WHERE mealDate = month)
    Meal findMealsByMonth(int month);
    */

    @Query("DELETE FROM MEAL WHERE mealDate = :date")
    void deletebyDate(int date);

    @Query("SELECT * FROM MEAL ORDER BY MealDate")
    LiveData<List<Meal>> getAllMeals();

    @Query("DELETE FROM Meal")
    void deleteAll();

    @Update
    void updateMeal(Meal meal);

    @Insert
    long insert(Meal meal);

    @Delete
    void delete(Meal...meals);

    /* for use if we extend to multiple meals
    @Query("SELECT * FROM MEAL WHERE MealID = :id LIMIT 1")
    Meal findMealByID(int id);
     */

}


