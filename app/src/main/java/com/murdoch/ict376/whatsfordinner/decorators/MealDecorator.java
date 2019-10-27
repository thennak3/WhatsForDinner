package com.murdoch.ict376.whatsfordinner.decorators;

import com.murdoch.ict376.whatsfordinner.Calendar;
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

public class MealDecorator implements DayViewDecorator {

    private int color;
    private ArrayList<CalendarDay> dates;

    public MealDecorator() {

        this.dates = new ArrayList();
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
        view.addSpan(new DotSpan(5, color));
    }

    public void setDates(List<LocalDate> displayDates) {
        ArrayList<CalendarDay> datesToAdd = new ArrayList<>();
        for(int i = 0;i<displayDates.size();i++)
            datesToAdd.add(CalendarDay.from(displayDates.get(i)));
        dates = datesToAdd;

    }
}
