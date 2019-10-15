package com.murdoch.ict376.whatsfordinner.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LetsEatRepository {

    private RecipeDAO mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipes;

    public LetsEatRepository(Application application) {
        LetsEatDatabase db = LetsEatDatabase.getDatabase(application);
        mRecipeDao = db.recipeDAO();
        mAllRecipes = mRecipeDao.getAllRecipes();
    }

    //Recipe

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public void insert(Recipe recipe) {
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe,Void,Void> {
        private RecipeDAO mAsyncTaskDao;

        insertAsyncTask(RecipeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
