package com.murdoch.ict376.whatsfordinner.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.murdoch.ict376.whatsfordinner.Calendar;
import com.murdoch.ict376.whatsfordinner.MainActivity;
import com.murdoch.ict376.whatsfordinner.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MealDecorator implements DayViewDecorator {

    private int color;
    private ArrayList<CalendarDay> dates;
    private Drawable drawable;

    public MealDecorator(Activity context) {

        this.dates = new ArrayList();
        drawable = context.getResources().getDrawable(R.drawable.meal_selected);
    }

    public MealDecorator(int color, ArrayList<CalendarDay> dates) {
        this.color = color;
        this.dates = new ArrayList(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }

    public void setDates(ArrayList<CalendarDay> displayDates) {

        dates = displayDates;

    }
}
