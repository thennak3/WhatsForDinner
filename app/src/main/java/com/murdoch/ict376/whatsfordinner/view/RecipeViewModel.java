package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import java.util.List;


//From my understanding this view model is specification to a particular view within the application, i.e. for the recipe list you'd pull in and expose different DAO's
//Basically you pick what functions you want from the Repository which contains all of the implementation of the database
//You handle pagination and the like in this class, pass requests to the repository


public class RecipeViewModel extends AndroidViewModel {
    private LetsEatRepository mRepository;

    private LiveData<List<Recipe>> mAllRecipes;

    public RecipeViewModel(Application application) {
        super(application);
        mRepository = new LetsEatRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mAllRecipes; }

    public void insert(Recipe recipe) { mRepository.insert(recipe); }
}
