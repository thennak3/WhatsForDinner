package com.murdoch.ict376.whatsfordinner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Fragment;

import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.view.MealViewModel;

public class MealDetailsFragment extends androidx.fragment.app.Fragment {

    private View mLayoutView;
    private MealViewModel model;
    private TextView recipename;
    private TextView mealinfo;

    private ImageView recipeImageView;


    MealViewModel mMealViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mLayoutView =  inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMealViewModel = ViewModelProviders.of(getActivity()).get(MealViewModel.class);

        recipename = mLayoutView.findViewById(R.id.text_recipename);
        mealinfo = mLayoutView.findViewById(R.id.text_mealinfo);
        recipeImageView = mLayoutView.findViewById(R.id.imageView);

        mMealViewModel.getRecipe().observe(getActivity(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if(recipe != null)
                {
                    if(recipe.GetImage() != null)
                    {
                        recipeImageView.setImageBitmap(recipe.GetImage());
                    }
                    else
                        recipeImageView.setImageResource(R.drawable.noimage);
                    recipename.setText(recipe.getName());
                }
                else
                {
                    recipeImageView.setImageResource(R.drawable.noimage);
                    recipename.setText("No Recipe Selected");
                }
            }
        });

        /* get viewmodel and attach observer
        model = ViewModelProviders.of(getActivity()).get(MealViewModel.class);
        model.get().observe(this, { item ->
                // Update the UI.
        });
        */




    }




}
