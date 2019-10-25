package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategoryDAO;
import com.murdoch.ict376.whatsfordinner.database.RecipeDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeListViewModel extends AndroidViewModel {

    public LiveData<List<Recipe>> recipeAllList;

    public MutableLiveData<Integer> filterCategoryAll = new MutableLiveData<Integer>();


    private LiveData<List<Category>> mAllCategories;

    LetsEatRepository mLetsEatRepository;

    public void initAllRecipes(){
        recipeAllList = Transformations.switchMap(filterCategoryAll, input -> {
            if(input == null || input.equals(-1)) {
                return mLetsEatRepository.getAllRecipes();
            }
            else
            {
                return mLetsEatRepository.getRecipesForCategory(filterCategoryAll.getValue());
            }
        });
    }

    public List<Category> getAllCategories() {
        try {
            return mLetsEatRepository.getAllCategoriesAsync();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public RecipeListViewModel(Application application){
        super(application);
        mLetsEatRepository = new LetsEatRepository(application);

        mAllCategories = mLetsEatRepository.getAllCategories();


    }
}
