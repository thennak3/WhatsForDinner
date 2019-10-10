package com.murdoch.ict376.whatsfordinner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class}, version = 1)
public abstract class LetsEatDatabase extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();

    private static volatile LetsEatDatabase INSTANCE;

    static LetsEatDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (LetsEatDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LetsEatDatabase.class,"letseat_database").build();
            }
        }
        return INSTANCE;
    }

}
