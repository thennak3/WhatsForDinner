package com.murdoch.ict376.whatsfordinner.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
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
        new insertAsyncTask(mRecipeDao,recipe).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;
        insertAsyncTask(RecipeDAO dao,Recipe recipe) {
            mAsyncRecipeDao = dao;
            this.recipe = recipe;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            //check what's been set
            if(mAsyncRecipeDao != null)
                mAsyncRecipeDao.insert(recipe);
            return null;
        }
    }

    public void update(Recipe recipe) { new updateAsyncTask(mRecipeDao,recipe).execute(); }

    private static class updateAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

            updateAsyncTask(RecipeDAO dao,Recipe recipe){
                mAsyncRecipeDao = dao;
                this.recipe = recipe;
            }

            @Override
            protected Void doInBackground(final Void... voids) {

                if(mAsyncRecipeDao != null) {
                    recipe.setDateModified(new Date(System.currentTimeMillis()));
                    mAsyncRecipeDao.updateRecipe(recipe);
                }
                return null;
            }
    }
}
