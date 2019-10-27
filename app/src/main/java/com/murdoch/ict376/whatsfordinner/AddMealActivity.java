package com.murdoch.ict376.whatsfordinner;

//import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.murdoch.ict376.whatsfordinner.view.MealViewModel;


import org.w3c.dom.Text;


public class AddMealActivity extends AppCompatActivity {

    AddMealFragment addmealfragment;
    MealDetailsFragment mealdetailsfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        addmealfragment = new AddMealFragment();
        mealdetailsfragment = new MealDetailsFragment();


        getFragmentManager().beginTransaction()
                .add(R.id.updatemeal_container, addmealfragment).commit();


        getFragmentManager().beginTransaction()
                .add(R.id.details_container, mealdetailsfragment).commit();


        /*
        final TextView date = findViewById(R.id.mealDate);
        TextView mealstatus = findViewById(R.id.text_meal_status);
        Button addoreditbutton = findViewById(R.id.addoredit_button);
        TextView recipename = findViewById(R.id.recipeName);
        TextView mealinfo = findViewById(R.id.textview_meal_info);


        //Button mealButton = findViewById(R.id.button_choose_meal);
        //LinearLayout mealLayout = findViewById(R.id.layout_meal_selected);

        Intent intent = getIntent();
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


        addoreditbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent returnIntent = new Intent();

                String mealDay = date.toString();

                returnIntent.putExtra(Calendar.RESULT, mealDay);
                setResult(Calendar.RESULT_OK, returnIntent);
                finish();


                //Intent intent = new Intent(this, RecipeList.class);
                //startActivity(intent);


            }
        });
        */
    }
}
