package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murdoch.ict376.whatsfordinner.database.Meal;
import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.helper.AfterDBOperationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MealViewModel extends AndroidViewModel {

    private LetsEatRepository mRepository;
    private LiveData<List<Meal>> mAllMeals;

    private AfterDBOperationListener delegate;

    public MealViewModel(Application application){
        super(application);
        mRepository = new LetsEatRepository(application);
        mAllMeals = mRepository.getAllMeals();
    }

    public LiveData<List<Meal>> getAllMeals() { return mAllMeals; }

    public void insert(Meal meal) { mRepository.insert(meal ,delegate); }
    public void update(Meal meal) { mRepository.update(meal); }

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
