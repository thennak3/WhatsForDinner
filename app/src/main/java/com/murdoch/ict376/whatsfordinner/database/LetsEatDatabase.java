package com.murdoch.ict376.whatsfordinner.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Recipe.class, Category.class,RecipeCategory.class}, version = 2)
public abstract class LetsEatDatabase extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract RecipeCategoryDAO recipeCategoryDAO();

    private static volatile LetsEatDatabase INSTANCE;


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    static LetsEatDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (LetsEatDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LetsEatDatabase.class,"letseat_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final RecipeDAO mRecipe;
        private final CategoryDAO mCategory;
        private final RecipeCategoryDAO mRecipeCategory;

        PopulateDbAsync(LetsEatDatabase db) {
            mRecipe = db.recipeDAO();
            mCategory = db.categoryDAO();
            mRecipeCategory = db.recipeCategoryDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mRecipe.deleteAll();
            Recipe recipe = new Recipe("Beef Lasagna");
            mRecipe.insert(recipe);
            recipe = new Recipe("Chimi Chungas");
            mRecipe.insert(recipe);

            mCategory.deleteAll();
            Category category = new Category("Beef");
            mCategory.insert(category);
            category = new Category("Chicken");
            mCategory.insert(category);
            return null;
        }
    }



}
