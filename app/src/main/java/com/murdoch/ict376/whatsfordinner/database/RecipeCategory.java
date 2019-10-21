package com.murdoch.ict376.whatsfordinner.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "RecipeCategory",
        primaryKeys = {"RecipeID", "CategoryID"},
        foreignKeys = {
            @ForeignKey(entity = Recipe.class,
                        parentColumns = "RecipeID",
                        childColumns = "RecipeID"),
            @ForeignKey(entity = Category.class,
                        parentColumns = "categoryID",
                        childColumns = "CategoryID" )
        },
        indices = {@Index(value ="CategoryID"),
                    @Index(value="RecipeID")}
        )
public class RecipeCategory {
    public final int RecipeID;
    public final int CategoryID;

    public RecipeCategory(final int RecipeID, final int CategoryID) {
        this.RecipeID = RecipeID;
        this.CategoryID = CategoryID;
    }
}
