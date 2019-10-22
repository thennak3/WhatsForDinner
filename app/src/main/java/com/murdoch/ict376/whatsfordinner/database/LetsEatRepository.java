package com.murdoch.ict376.whatsfordinner.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class LetsEatRepository {

    private RecipeDAO mRecipeDao;
    private CategoryDAO mCategoryDao;
    private LiveData<List<Recipe>> mAllRecipes;

    private LiveData<List<Category>> mAllCategories;

    public LetsEatRepository(Application application) {
        LetsEatDatabase db = LetsEatDatabase.getDatabase(application);
        mRecipeDao = db.recipeDAO();
        mCategoryDao = db.categoryDAO();
        mAllRecipes = mRecipeDao.getAllRecipes();

    }

    //Recipe

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public LiveData<List<Category>> getAllCategories() {
        if(mAllCategories == null)
            mAllCategories = mCategoryDao.getAllCategories();
        return mAllCategories;
    }

    public void insert(Recipe recipe) {
        new insertAsyncTask(mRecipeDao,recipe).execute();
    }

    public void insert(Category category) { new insertAsyncTask(mCategoryDao,category).execute(); }

    public RecipeDAO getRecipeDao(){
        return mRecipeDao;
    }

    public CategoryDAO getCategoryDao() {
        return mCategoryDao;
    }

    private static class insertAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;

        insertAsyncTask(RecipeDAO dao,Recipe recipe) {
            mAsyncRecipeDao = dao;
            this.recipe = recipe;
        }

        insertAsyncTask(CategoryDAO categoryDAO, Category category) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            //check what's been set
            if(mAsyncRecipeDao != null)
                mAsyncRecipeDao.insert(recipe);
            if(mAsyncCategoryDao != null)
                mAsyncCategoryDao.insert(category);
            return null;
        }
    }

    public void update(Recipe recipe) { new updateAsyncTask(mRecipeDao,recipe).execute(); }

    public void update(Category category) { new updateAsyncTask(mCategoryDao,category).execute(); }

    private static class updateAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;


        updateAsyncTask(RecipeDAO dao,Recipe recipe){
                mAsyncRecipeDao = dao;
                this.recipe = recipe;
            }


        updateAsyncTask(CategoryDAO categoryDAO, Category category) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
        }

            @Override
            protected Void doInBackground(final Void... voids) {

                if(mAsyncRecipeDao != null) {
                    recipe.setDateModified(new Date(System.currentTimeMillis()));
                    mAsyncRecipeDao.updateRecipe(recipe);
                }
                if(mAsyncCategoryDao != null) {
                    mAsyncCategoryDao.update(category);
                }
                return null;
            }
    }


    private static class deleteAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;

        deleteAsyncTask(RecipeDAO dao,Recipe recipe) {
            mAsyncRecipeDao = dao;
            this.recipe = recipe;
        }

        deleteAsyncTask(CategoryDAO categoryDAO, Category category) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            //check what's been set
            if(mAsyncRecipeDao != null)
                mAsyncRecipeDao.delete(recipe);
            if(mAsyncCategoryDao != null)
                mAsyncCategoryDao.delete(category);
            return null;
        }
    }
}
