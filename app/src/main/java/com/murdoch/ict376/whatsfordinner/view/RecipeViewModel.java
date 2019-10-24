package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.helper.AfterDBOperationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


//From my understanding this view model is specification to a particular view within the application, i.e. for the recipe list you'd pull in and expose different DAO's
//Basically you pick what functions you want from the Repository which contains all of the implementation of the database
//You handle pagination and the like in this class, pass requests to the repository


public class RecipeViewModel extends AndroidViewModel {
    private LetsEatRepository mRepository;

    private LiveData<List<Recipe>> mAllRecipes;
    private LiveData<List<Category>> mAllCategories;

    private AfterDBOperationListener delegate;

    public RecipeViewModel(Application application) {
        super(application);
        mRepository = new LetsEatRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
        mAllCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mAllRecipes; }

    public void insert(Recipe recipe) { mRepository.insert(recipe,delegate); }
    public void insert(RecipeCategory recipeCategory) { mRepository.insert(recipeCategory); }

    public void update(Recipe recipe) {mRepository.update(recipe); }

    public LiveData<List<Category>> getAllCategories() { return mAllCategories; }

    public List<RecipeCategory> getAllRecipeCategoriesByRecipeID(int id) {
        try {
            return mRepository.getRecipeCategoryByRecipeID(id);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setDelegate(AfterDBOperationListener delegate) {
        this.delegate = delegate;
    }

    public void deleteRecipeCategory(Long id) { mRepository.deleteRecipeCategory(id); }
}
