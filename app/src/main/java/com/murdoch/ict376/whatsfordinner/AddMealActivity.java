package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
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
