package com.murdoch.ict376.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.view.RecipeListAdapter;
import com.murdoch.ict376.whatsfordinner.view.RecipeListViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;
import com.sayantan.advancedspinner.MultiSpinner;
import com.sayantan.advancedspinner.MultiSpinnerListener;
import com.sayantan.advancedspinner.SingleSpinner;
import com.sayantan.advancedspinner.SpinnerListener;

import java.util.ArrayList;
import java.util.List;

import static com.murdoch.ict376.whatsfordinner.RecipeList.EDIT_RECIPE_ACTIVITY_REQUEST_CODE;


public class SelectRecipeListActivity extends AppCompatActivity implements RecyclerViewClickListener {

    public static final String RECIPEREPLY = "com.murdoch.ict376.whatsfordinner.RECIPE.SELECTED";

    RecipeListViewModel mRecipeViewModel;

    RecipeListAdapter adapter;

    List<Category> mCategories;

    SingleSpinner mCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        RecyclerView recyclerView = findViewById(R.id.select_recipe_recycler);
        adapter = new RecipeListAdapter(this, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        mRecipeViewModel.initAllRecipes();
        mRecipeViewModel.filterCategoryAll.setValue(-1);
        mRecipeViewModel.recipeAllList.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });


        mCategorySpinner = findViewById(R.id.category_recipe_spinner);
        mCategories = mRecipeViewModel.getAllCategories();

        List<String> categoryNames = new ArrayList<>();
        if (mCategories != null) {
            for (int i = 0; i < mCategories.size(); i++) {
                categoryNames.add(mCategories.get(i).getName());
            }
            mCategorySpinner.setSpinnerList(categoryNames);
            mCategorySpinner.selectNone();

            mCategorySpinner.addOnItemChoosenListener(new SpinnerListener() {
                @Override
                public void onItemChoosen(String s, int i) {
                    if(i == -1)
                    {
                        mRecipeViewModel.filterCategoryAll.setValue(-1);
                    }
                    else
                    {
                        mRecipeViewModel.filterCategoryAll.setValue(mCategories.get(i).categoryID);
                    }
                }
            });

        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        //return result

        Intent replyIntent = new Intent();
        replyIntent.putExtra(RECIPEREPLY, adapter.getRecipe(position).getRecipeID());
        setResult(RESULT_OK, replyIntent);
        finish();

    }
}
