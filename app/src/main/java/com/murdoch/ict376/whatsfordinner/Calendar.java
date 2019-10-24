package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.murdoch.ict376.whatsfordinner.decorators.MealDecorator;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Calendar extends AppCompatActivity implements OnDateSelectedListener {

    private MaterialCalendarView mealCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_fragment);
        mealCalendar = (MaterialCalendarView) findViewById(R.id.mealCalendar); // get the reference of CalendarView

        mealCalendar.setOnDateChangedListener(this);
        mealCalendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        final LocalDate instance = LocalDate.now();
        mealCalendar.setSelectedDate(instance);

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView mealCalendar,
            @NonNull CalendarDay date,
            boolean selected) {

        //oneDayDecorator.setDate(date.getDate());
        mealCalendar.invalidateDecorators();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
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
    protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
        super.onPostExecute(calendarDays);

        if (isFinishing()) {
            return;
        }

        mealCalendar.addDecorator(new MealDecorator(Color.RED, calendarDays));
    }
}
}
