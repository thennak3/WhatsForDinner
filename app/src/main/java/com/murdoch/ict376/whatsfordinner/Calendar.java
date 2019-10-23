package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.murdoch.ict376.whatsfordinner.view.CalendarViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecipeListAdapter;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;

import java.util.List;

public class Calendar extends AppCompatActivity {

    private CalendarViewModel mCalendarViewModel;
    private MaterialCalendarView mealCalendar;
    //private ArrayList<Date> markedDates;


    //RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_fragment);


        setContentView(R.layout.calendar_fragment);
        mealCalendar = (MaterialCalendarView) findViewById(R.id.mealCalendar); // get the reference of CalendarView
        /*mealCalendar.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        mealCalendar.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        mealCalendar.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        mealCalendar.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        mealCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
        */
    }
}
