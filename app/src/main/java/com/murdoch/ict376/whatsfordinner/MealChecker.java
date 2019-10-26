package com.murdoch.ict376.whatsfordinner;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class MealChecker {

    private boolean hasMeal = false;


    public boolean run(CalendarDay mealCalendar) {

        LocalDate temp = LocalDate.now().minusMonths(2);
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final CalendarDay day = CalendarDay.from(temp);
            dates.add(day);
            temp = temp.plusDays(5);
        }

        if (dates.contains(mealCalendar))
        {
            hasMeal = true;
        }

        return hasMeal;

    }

}
