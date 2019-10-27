package com.murdoch.ict376.whatsfordinner;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class AddMealFragment extends Fragment{

    private View mLayoutView;
    private Button addoreditbutton;
    private TextView mealstatus;
    private TextView mealdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mLayoutView =  inflater.inflate(R.layout.fragment_add_meal, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mealdate = mLayoutView.findViewById(R.id.text_mealdate);
        addoreditbutton = mLayoutView.findViewById(R.id.addoredit_button);
        mealstatus = mLayoutView.findViewById(R.id.text_mealstatus);


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

                Intent intent = new Intent(getActivity(), RecipeList.class);
                startActivity(intent);


            }
        });
    }

}
