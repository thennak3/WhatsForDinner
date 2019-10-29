package com.murdoch.ict376.whatsfordinner.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.murdoch.ict376.whatsfordinner.helper.AfterDBOperationListener;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LetsEatRepository {

    //dao variables
    private RecipeDAO mRecipeDao;
    private CategoryDAO mCategoryDao;
    private RecipeCategoryDAO mRecipeCategoryDao;
    private MealDAO mMealDao;

    //livedata variables
    private LiveData<List<Recipe>> mAllRecipes;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<Recipe>> mRecipesFilteredCategory;
    private LiveData<List<Meal>> mAllMeals;

    public LetsEatRepository(Application application) {
        LetsEatDatabase db = LetsEatDatabase.getDatabase(application);
        mRecipeDao = db.recipeDAO();
        mCategoryDao = db.categoryDAO();
        mRecipeCategoryDao = db.recipeCategoryDAO();
        mAllRecipes = mRecipeDao.getAllRecipes();
        mMealDao = db.mealDAO();
        mAllMeals = mMealDao.getAllMeals();

    }

    //Recipe

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public LiveData<List<Meal>> getAllMeals(){
        return mAllMeals;
    }

    public List<Category> getAllCategoriesAsync() throws ExecutionException, InterruptedException {
        return new getAllAsyncTaskCategory(mCategoryDao).execute().get();
    }


    //get methods for LiveData encapsulated Lists of data - for use in the View Models
    public LiveData<List<Category>> getAllCategories() {
        if(mAllCategories == null)
            mAllCategories = mCategoryDao.getAllCategories();
        return mAllCategories;
    }

    public LiveData<List<Recipe>> getAllRecipesByCategoryID(int id) {
        mRecipesFilteredCategory = mRecipeCategoryDao.getRecipesForCategory(id);
        return mRecipesFilteredCategory;
    }

    public LiveData<List<Recipe>> getRecipesForCategory(Integer value) {
        return mRecipeCategoryDao.getRecipesForCategory(value);
    }



    //get methods for lists of data
    public List<RecipeCategory> getRecipeCategoryByRecipeID(int id) throws ExecutionException, InterruptedException {
        return new getAllAsyncTask(mRecipeCategoryDao,id).execute().get();
    }

    public List<RecipeCategory> getRecipeCategoryByCategoryID(int id) {
        return mRecipeCategoryDao.getRecipeCategoriesByCategoryID(id);
    }

    public LiveData<List<Meal>> getMealsByDates(Date startDate, Date endDate) {
        return mMealDao.getMealsByDates(startDate,endDate);
    }

    public LiveData<Recipe> getRecipe(Integer input) {
        return mRecipeDao.getRecipe(input);
    }


    private static class getAllAsyncTask extends AsyncTask<Void,Void,List<RecipeCategory>> {
        private RecipeCategoryDAO mRecipeCategoryDao;
        List<RecipeCategory> rc;

        int id;

        getAllAsyncTask(RecipeCategoryDAO dao, int id) {
            mRecipeCategoryDao = dao;
            this.id = id;
        }

        @Override
        protected List<RecipeCategory> doInBackground(Void...voids) {
            return mRecipeCategoryDao.getRecipeCategoriesByRecipeID(id);
        }
    }

    private static class getAllAsyncTaskCategory extends AsyncTask<Void,Void,List<Category>> {
        private CategoryDAO mCategoryDao;
        List<Category> cat;



        getAllAsyncTaskCategory(CategoryDAO dao) {
            mCategoryDao = dao;
        }

        @Override
        protected List<Category> doInBackground(Void...voids) {
            return mCategoryDao.getAllCategoriesList();
        }
    }


    //DAO get methods
    public RecipeDAO getRecipeDao(){
        return mRecipeDao;
    }
    public CategoryDAO getCategoryDao() {
        return mCategoryDao;
    }
    public MealDAO getmMealDao() {return mMealDao;}

    //expose insertAsyncTest class to outer class
    public void insert(Recipe recipe) { new insertAsyncTask(mRecipeDao,recipe,null).execute(); }
    public void insert(Category category) { new insertAsyncTask(mCategoryDao,category,null).execute(); }
    public void insert(Recipe recipe,AfterDBOperationListener listener) { new insertAsyncTask(mRecipeDao,recipe,listener).execute(); }
    public void insert(Category category,AfterDBOperationListener listener) { new insertAsyncTask(mCategoryDao,category,listener).execute(); }
    public void insert(RecipeCategory recipeCategory) { new insertAsyncTask(mRecipeCategoryDao,recipeCategory, null).execute(); }
    public void insert(Meal meal, AfterDBOperationListener listener) { new insertAsyncTask(mMealDao, meal, listener).execute(); }

    //Async insert implementation for database
    private static class insertAsyncTask extends AsyncTask<Void,Void,Long> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;

        private RecipeCategoryDAO mAsyncRecipeCategoryDao;
        private RecipeCategory recipeCategory;

        private MealDAO mAsyncMealDao;
        private Meal meal;

        private WeakReference<AfterDBOperationListener> asyncDelegate;

        insertAsyncTask(RecipeDAO dao, Recipe recipe, AfterDBOperationListener listener) {
            mAsyncRecipeDao = dao;
            this.recipe = recipe;
            asyncDelegate = new WeakReference<>(listener);
        }

        insertAsyncTask(CategoryDAO categoryDAO, Category category, AfterDBOperationListener listener) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
            asyncDelegate = new WeakReference<>(listener);
        }

        insertAsyncTask(RecipeCategoryDAO recipeCategoryDAO, RecipeCategory recipeCategory, AfterDBOperationListener listener) {
            this.mAsyncRecipeCategoryDao = recipeCategoryDAO;
            this.recipeCategory = recipeCategory;
            asyncDelegate = new WeakReference<>(listener);
        }

        insertAsyncTask(MealDAO dao, Meal meal, AfterDBOperationListener listener) {
            this.mAsyncMealDao = dao;
            this.meal = meal;
            asyncDelegate = new WeakReference<>(listener);
        }

        @Override
        protected Long doInBackground(final Void... voids) {
            //check what's been set
            if(mAsyncRecipeDao != null)
                return mAsyncRecipeDao.insert(recipe);
            if(mAsyncCategoryDao != null)
                return mAsyncCategoryDao.insert(category);
            if(mAsyncRecipeCategoryDao != null)
                mAsyncRecipeCategoryDao.insert(recipeCategory);
            if(mAsyncMealDao != null)
                mAsyncMealDao.insert(meal);

            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            final AfterDBOperationListener delegate = asyncDelegate.get();
            if(delegate != null)
                delegate.afterDBOperation(result);
        }
    }

    //Async update implementation for database

    public void update(Recipe recipe) { new updateAsyncTask(mRecipeDao,recipe).execute(); }

    public void update(Category category) { new updateAsyncTask(mCategoryDao,category).execute(); }

    public void update(Meal meal) { new updateAsyncTask(mMealDao,meal).execute(); }


    private static class updateAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;

        private MealDAO mAsyncMealDao;
        private Meal meal;


        //Overloading to handle different database tables
        updateAsyncTask(RecipeDAO dao,Recipe recipe){
                mAsyncRecipeDao = dao;
                this.recipe = recipe;
            }

        updateAsyncTask(CategoryDAO categoryDAO, Category category) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
        }

        updateAsyncTask(MealDAO dao, Meal meal) {
            this.mAsyncMealDao = dao;
            this.meal = meal;
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
                if(mAsyncMealDao != null) {
                    mAsyncMealDao.updateMeal(meal);
                }

                return null;
            }
    }

    //Async delete implementation for database

    public void deleteRecipeCategory(Long id) { new deleteAsyncTask(mRecipeCategoryDao,id).execute(); }

    public void deleteMealbyDate(Date date) { new deleteAsyncTask(mMealDao,date).execute(); }

    private static class deleteAsyncTask extends AsyncTask<Void,Void,Void> {
        private RecipeDAO mAsyncRecipeDao;
        private Recipe recipe;

        private CategoryDAO mAsyncCategoryDao;
        private Category category;

        private RecipeCategoryDAO mAsyncRecipeCategoryDao;
        private Long id;

        private MealDAO mAsyncMealDao;
        private Meal meal;
        private Date date;

        deleteAsyncTask(RecipeDAO dao,Recipe recipe) {
            mAsyncRecipeDao = dao;
            this.recipe = recipe;
        }

        deleteAsyncTask(CategoryDAO categoryDAO, Category category) {
            this.mAsyncCategoryDao = categoryDAO;
            this.category = category;
        }

        deleteAsyncTask(RecipeCategoryDAO recipeCategoryDAO, Long id) {
            this.mAsyncRecipeCategoryDao = recipeCategoryDAO;
            this.id = id;
        }

        deleteAsyncTask(MealDAO dao, Meal meal) {
            this.mAsyncMealDao = dao;
            this.meal = meal;
        }

        deleteAsyncTask(MealDAO dao, Date date) {
            this.mAsyncMealDao = dao;
            this.date = date;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            //check what's been set
            if(mAsyncRecipeDao != null)
                mAsyncRecipeDao.delete(recipe);
            if(mAsyncCategoryDao != null)
                mAsyncCategoryDao.delete(category);
            if(mAsyncRecipeCategoryDao != null)
                mAsyncRecipeCategoryDao.delete(id);
            if(mAsyncMealDao != null && meal != null)
                mAsyncMealDao.delete(meal);
            if(mAsyncMealDao != null && date != null)
                mAsyncMealDao.deletebyDate(date);
            return null;
        }
    }
}
