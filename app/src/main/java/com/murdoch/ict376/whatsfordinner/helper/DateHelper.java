package com.murdoch.ict376.whatsfordinner.helper;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }

    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static String SimpleDate(Date day)
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(day);
    }

    public static LocalDate toLocalDate(Date date)
    {
        return org.threeten.bp.DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static CalendarDay toCalendarDay(Date date) {
        return CalendarDay.from(toLocalDate(date));
    }

    public static Date toDate(CalendarDay calendarDay)
    {
        return org.threeten.bp.DateTimeUtils.toDate(calendarDay.getDate().atStartOfDay().toInstant(ZoneOffset.UTC));
    }
}
