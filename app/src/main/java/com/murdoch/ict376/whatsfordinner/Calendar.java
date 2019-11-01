package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.murdoch.ict376.whatsfordinner.database.Meal;
import com.murdoch.ict376.whatsfordinner.helper.DateHelper;
import com.murdoch.ict376.whatsfordinner.view.MealFilter;
import com.murdoch.ict376.whatsfordinner.view.MealViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.murdoch.ict376.whatsfordinner.decorators.MealDecorator;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;

public class Calendar extends Fragment implements OnDateSelectedListener {

    private MaterialCalendarView mealCalendar;
    private static final int ADD_MEAL = 48;
    public static final String RESULT = "result";
    private boolean mealSelected;

    private MealDecorator mealDecorator;

    MealViewModel mMealViewModel;

    View RootView;

    HashMap<CalendarDay,Meal> mealList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        mealDecorator = new MealDecorator(getActivity());

        return RootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mealCalendar = (MaterialCalendarView) RootView.findViewById(R.id.mealCalendar); // get the reference of CalendarView

        mealCalendar.setOnDateChangedListener(this);
        mealCalendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        mMealViewModel = ViewModelProviders.of(this).get(MealViewModel.class);


        final LocalDate instance = LocalDate.now();
        mealCalendar.setSelectedDate(instance);
        mealCalendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(getContext(),"Changed month " + date.getDate().minusMonths(1).toString() + " to " + date.getDate().plusMonths(1).toString(),Toast.LENGTH_SHORT).show();
                MealFilter mealFilter = new MealFilter();
                mealFilter.startDate = org.threeten.bp.DateTimeUtils.toDate(date.getDate().minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                mealFilter.endDate = org.threeten.bp.DateTimeUtils.toDate(date.getDate().plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                mMealViewModel.filterMeals(mealFilter);
            }
        });

        MealFilter mealFilter = new MealFilter();
        mealFilter.startDate = org.threeten.bp.DateTimeUtils.toDate(instance.minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        mealFilter.endDate = org.threeten.bp.DateTimeUtils.toDate(instance.plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        mMealViewModel.filterMeals(mealFilter);

        mMealViewModel.getMeals().observe(getActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                MealsUpdated(meals);
            }
        });

        mealCalendar.addDecorator(mealDecorator);

        //new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

    }

    void MealsUpdated(List<Meal> meals) {

        Toast.makeText(getContext(),"Meals list updated",Toast.LENGTH_SHORT).show();

        ArrayList<CalendarDay> dates = new ArrayList<>();

        mealList = new HashMap<>();
        CalendarDay temp;
        for(int i = 0;i<meals.size();i++)
        {
            //convert date to livedate, add to list and give to your decorator class here..
            temp = DateHelper.toCalendarDay(meals.get(i).getMealDate());
            dates.add(temp);
            mealList.put(temp,meals.get(i));
        }

        mealDecorator.setDates(dates);
        mealCalendar.invalidateDecorators();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == ADD_MEAL && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "Meal Added", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMeal() {
        Intent intent = new Intent(getActivity(), AddMealActivity.class);
        CalendarDay day = mealCalendar.getSelectedDate();
        intent.putExtra("DATE_SET", DateHelper.toDate(day));
        if(mealList.containsKey(mealCalendar.getSelectedDate())){
            intent.putExtra("MEAL_SELECTED", true);
            intent.putExtra("RECIPE_ID",mealList.get(day).getRecipeID());
        }
        else
            intent.putExtra("MEAL_SELECTED", false);

        startActivityForResult(intent, ADD_MEAL);
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView mealCalendar,
            @NonNull CalendarDay date,
            boolean selected) {

        mealCalendar.invalidateDecorators();
        Log.d("DATE", "onDateSelected: date= " + date.toString());
        Log.d("DATE", "onDateSelected: selected date= " + mealCalendar.getSelectedDate().toString());
        Log.d("DATE", "onDateSelected: current date= " + mealCalendar.getCurrentDate().toString());
        //Toast.makeText(getApplicationContext(), mealCalendar.getSelectedDate().toString(), Toast.LENGTH_SHORT).show();

        boolean mealDateSelected = MealChecker.run(date);

        if (mealDateSelected)
        {
            mealSelected = true;
            addMeal();
        }
        else
        {
            mealSelected = false;
            addMeal();
        }




    }

    /*
    private class ApiSimulator extends AsyncTask<Void, Void, ArrayList<CalendarDay>> {

        @Override
        protected ArrayList<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull ArrayList<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

        }

    }*/

}
