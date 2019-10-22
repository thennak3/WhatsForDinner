package com.murdoch.ict376.whatsfordinner.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.LetsEatRepository;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private LetsEatRepository letsEatRepository;

    private LiveData<List<Category>> mCategory;

    public CategoryViewModel(Application application)
    {
        super(application);
        letsEatRepository = new LetsEatRepository(application);
        mCategory = letsEatRepository.getAllCategories();
    }

    public void insert(Category category) { letsEatRepository.insert(category);}

    public void update(Category category) { letsEatRepository.update(category);}

    public LiveData<List<Category>> getAllCategories() { return mCategory; }

}
