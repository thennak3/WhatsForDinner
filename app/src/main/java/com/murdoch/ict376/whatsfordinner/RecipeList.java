package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.helper.AfterDBOperationListener;
import com.murdoch.ict376.whatsfordinner.view.RecipeListAdapter;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RecipeList extends androidx.fragment.app.Fragment implements RecyclerViewClickListener, AfterDBOperationListener {

    private RecipeViewModel mRecipeViewModel;

    public static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 2;

    RecipeListAdapter adapter;

    FloatingActionButton floatingActionButtonView;

    View RootView;

    int[] categoryData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.activity_recipe_list, container, false);
        return RootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RecyclerView recyclerView = RootView.findViewById(R.id.recyclerview);
        adapter = new RecipeListAdapter(getActivity(), this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        floatingActionButtonView = RootView.findViewById(R.id.recipe_fab);

        floatingActionButtonView.setOnClickListener(this::fabOnClick);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipes().observe(getActivity(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Recipe recipe = (Recipe) data.getSerializableExtra(NewRecipeActivity.EXTRA_REPLY);
            categoryData = (int[]) data.getIntArrayExtra(NewRecipeActivity.EXTRA_CATEGORIES);
            mRecipeViewModel.setDelegate(this);
            mRecipeViewModel.insert(recipe);
        } else if (requestCode == EDIT_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Recipe recipe = (Recipe) data.getSerializableExtra(NewRecipeActivity.EXTRA_REPLY);
            categoryData = (int[]) data.getIntArrayExtra(NewRecipeActivity.EXTRA_CATEGORIES);
            mRecipeViewModel.update(recipe);
            afterDBOperation(Long.valueOf(recipe.getRecipeID()));
        } else {
            Toast.makeText(getContext(), R.string.empty_recipe_not_saved, Toast.LENGTH_SHORT).show();
        }
    }


    public void fabOnClick(View view) {
        Intent intent = new Intent(getActivity(), NewRecipeActivity.class);
        startActivityForResult(intent, NEW_RECIPE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent intent = NewRecipeActivity.GetEditRecipeIntent(getActivity(), adapter.getRecipe(position));
        startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void afterDBOperation(Long result)
    {

        mRecipeViewModel.deleteRecipeCategory(result);
        for(int i = 0;i<categoryData.length;i++){
            RecipeCategory newcat = new RecipeCategory(result.intValue(),categoryData[i]);
            mRecipeViewModel.insert(newcat);
        }

    }
}
