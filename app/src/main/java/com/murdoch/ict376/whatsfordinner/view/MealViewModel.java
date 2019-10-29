package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.murdoch.ict376.whatsfordinner.database.Meal;
import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.helper.AfterDBOperationListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MealViewModel extends AndroidViewModel {

    private LetsEatRepository mRepository;
    private LiveData<List<Meal>> mMeals;

    MutableLiveData<MealFilter> mealFilter = new MutableLiveData<>();

    MutableLiveData<Integer> recipeFilter = new MutableLiveData<>();

    LiveData<Recipe> mRecipe;

    private AfterDBOperationListener delegate;

    public MealViewModel(Application application){
        super(application);
        mRepository = new LetsEatRepository(application);

        mMeals = Transformations.switchMap(mealFilter, new Function<MealFilter, LiveData<List<Meal>>>() {
            @Override
            public LiveData<List<Meal>> apply(MealFilter input) {
                return mRepository.getMealsByDates(input.startDate,input.endDate);
            }
        });

        mRecipe = Transformations.switchMap(recipeFilter, new Function<Integer, LiveData<Recipe>>() {
            @Override
            public LiveData<Recipe> apply(Integer input) {
                return mRepository.getRecipe(input);
            }
        });
    }

    public void filterMeals(MealFilter mealFilter)
    {
        this.mealFilter.postValue(mealFilter);
    }

    public void filterRecipe(int RecipeID)
    {
        this.recipeFilter.postValue(RecipeID);
    }

    public LiveData<List<Meal>> getMeals() { return mMeals; }

    public LiveData<Recipe> getRecipe() { return mRecipe; }

    public void insert(Meal meal) { mRepository.insert(meal ,delegate); }
    public void update(Meal meal) { mRepository.update(meal); }
    public void delete(Date date) { mRepository.deleteMealbyDate(date); }



    /*
    public List<Meal> getAllMealsByMonth(int month) {
        try {
            return mRepository.getAllMeals()

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


     */


    public void setDelegate(AfterDBOperationListener delegate) {
        this.delegate = delegate;
    }


}
