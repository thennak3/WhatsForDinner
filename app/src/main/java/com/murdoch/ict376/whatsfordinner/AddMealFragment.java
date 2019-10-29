package com.murdoch.ict376.whatsfordinner;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.murdoch.ict376.whatsfordinner.database.Meal;
import com.murdoch.ict376.whatsfordinner.helper.DateHelper;
import com.murdoch.ict376.whatsfordinner.view.MealViewModel;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class AddMealFragment extends androidx.fragment.app.Fragment{

    private View mLayoutView;
    private Button addoreditbutton;
    private TextView mealstatus;
    private TextView mealdate;
    MealViewModel mMealViewModel;

    final int SELECT_RECIPE_RESPONSE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mLayoutView =  inflater.inflate(R.layout.fragment_add_meal, container, false);
    }

    public Date getDate() { return (Date)getArguments().getSerializable("date");}
    public int getMealID() { return getArguments().getInt("MealID"); }

    public static AddMealFragment newInstance(Date date,int MealID)
    {
        AddMealFragment f = new AddMealFragment();
        Bundle args = new Bundle();
        args.putSerializable("date",date);
        args.putInt("MealID",MealID);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMealViewModel = ViewModelProviders.of(getActivity()).get(MealViewModel.class);

        mealdate = mLayoutView.findViewById(R.id.text_mealdate);
        addoreditbutton = mLayoutView.findViewById(R.id.addoredit_button);
        mealstatus = mLayoutView.findViewById(R.id.text_mealstatus);

        if(getMealID() != -1)
            addoreditbutton.setText("Select Recipe");
        mMealViewModel.filterRecipe(getMealID());

        mealdate.setText("Meal Date " + DateHelper.SimpleDate(DateHelper.getStartOfDay(getDate())));




        /* Use ViewModel instead


        Intent intent = getActivity().getIntent();
        String setDate = intent.getStringExtra("DATE_SET");
        boolean mealSelected = intent.getBooleanExtra("MEAL_SELECTED", false);




        if (setDate != null);
        {
            date.setText(setDate);
        }

        if (mealSelected)
        {
            Log.d("mealSelected", "onCreate: TRUE");
            mealstatus.setText("Meal Selected");
            addoreditbutton.setText("Change Selected Meal");
            //mealLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.d("mealSelected", "onCreate: FALSE");
            mealstatus.setText("Meal Not Selected");
            addoreditbutton.setText("Select Meal");
        }


         */

        addoreditbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent returnIntent = new Intent();
                String mealDay = date.toString();
                returnIntent.putExtra(Calendar.RESULT, mealDay);
                setResult(Calendar.RESULT_OK, returnIntent);
                finish();
                */

                Intent intent = new Intent(getActivity(), SelectRecipeListActivity.class);
                startActivityForResult(intent, SELECT_RECIPE_RESPONSE);


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data)
    {
       if(resultCode == RESULT_OK)
       {
           int recipeID = data.getIntExtra(SelectRecipeListActivity.RECIPEREPLY,-1);
           mMealViewModel.delete(DateHelper.getStartOfDay(getDate()));
           Meal meal = new Meal();
           meal.setMealDate(DateHelper.getStartOfDay(getDate()));
           meal.setRecipeID(recipeID);
           mMealViewModel.insert(meal);
           mMealViewModel.filterRecipe(recipeID);
           addoreditbutton.setText("Select Recipe");

       }
        super.onActivityResult(requestCode,resultCode,data);
    }

}
