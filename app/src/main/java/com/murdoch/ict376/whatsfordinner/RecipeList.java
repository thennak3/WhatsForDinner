package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.view.RecipeListAdapter;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;

import java.util.List;

public class RecipeList extends AppCompatActivity implements RecyclerViewClickListener {

    private RecipeViewModel mRecipeViewModel;

    public static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_RECIPE_ACTIVITY_REQUEST_CODE = 2;

    RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecipeListAdapter(this,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == NEW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Recipe recipe = (Recipe)data.getSerializableExtra(NewRecipeActivity.EXTRA_REPLY);
            mRecipeViewModel.insert(recipe);

        } else if(requestCode == EDIT_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Recipe recipe = (Recipe)data.getSerializableExtra(NewRecipeActivity.EXTRA_REPLY);
            mRecipeViewModel.update(recipe);
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.empty_recipe_not_saved, Toast.LENGTH_SHORT).show();
        }
    }



    public void fabOnClick(View view)
    {
        Intent intent = new Intent(RecipeList.this,NewRecipeActivity.class);
        startActivityForResult(intent, NEW_RECIPE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent intent = NewRecipeActivity.GetEditRecipeIntent(RecipeList.this,adapter.getRecipe(position));
        startActivityForResult(intent, EDIT_RECIPE_ACTIVITY_REQUEST_CODE);
    }
}
