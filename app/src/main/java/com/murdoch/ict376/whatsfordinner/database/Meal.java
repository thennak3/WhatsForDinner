package com.murdoch.ict376.whatsfordinner.database;

//android imports
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

//java imports
import java.util.Date;
import java.io.Serializable;


@Entity
@TypeConverters(DateTypeConverter.class)
public class Meal implements Serializable {

    /* For use if we extend to multiple meals per day
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "MealID")
    private int mealID;
    */

    @PrimaryKey
    @ColumnInfo(name = "MealDate")
    private Date mealDate;

    @ColumnInfo(name = "UserID")
    private int userID;

    @ColumnInfo (name = "MealTypeID")
    private int mealTypeID;

    //limit to recipeID
    @ColumnInfo (name = "RecipeID")
    private int recipeID;

    public Meal(int id, Date date)
    {
        this.recipeID = id;
        this.mealDate = date;
    }

    public Meal()
    {

    }

    public Date getMealDate(){return this.mealDate; }

    public int getUserID(){return this.userID; }

    public int getMealTypeID(){return this.mealTypeID; }

    public int getRecipeID(){return this.recipeID; }

    public void setMealDate(Date d){this.mealDate = d; }

    public void setUserID(int id){this.userID = id; }

    public void setMealTypeID(int id){this.mealTypeID = id; }

    public void setRecipeID(int id){this.recipeID = id; }


}
