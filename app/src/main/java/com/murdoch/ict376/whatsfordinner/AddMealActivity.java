package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import org.threeten.bp.LocalDate;


public class AddMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        final TextView date = (TextView) findViewById(R.id.mealDate);
        Button button = (Button) findViewById(R.id.button);
        Button mealButton = (Button) findViewById(R.id.button_choose_meal);
        LinearLayout mealLayout = (LinearLayout) findViewById(R.id.layout_meal_selected);

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
            mealButton.setText("Change Selected Meal");
            mealLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.d("mealSelected", "onCreate: FALSE");
            mealButton.setText("Select Meal");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                String mealDay = date.toString();

                returnIntent.putExtra(Calendar.RESULT, mealDay);
                setResult(Calendar.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
