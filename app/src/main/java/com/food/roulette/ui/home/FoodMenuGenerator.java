package com.food.roulette.ui.home;

import android.icu.util.Calendar;
import android.util.Log;

import com.food.roulette.ui.preferences.PreferencesHandler;


import java.util.ArrayList;

public class FoodMenuGenerator {

    enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    };

    public FoodMenuGenerator()
    {
    }

    public boolean isWeekend()
    {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek == Day.SATURDAY.ordinal() || dayOfWeek == Day.SUNDAY.ordinal();
    }

    public String getBreakfast()
    {
        ArrayList<String> breakfastList = PreferencesHandler.getInstance().getBreakfastList();
        int index = (int)(Math.random() * breakfastList.size());
        return breakfastList.get(index);
    }

    public String getLunch()
    {
        ArrayList<String> lunchList = PreferencesHandler.getInstance().getLunchList();
        int index = (int)(Math.random() * lunchList.size());
        return lunchList.get(index);
    }

    public String getDinner()
    {
        ArrayList<String> dinnerList = PreferencesHandler.getInstance().getDinnerList();
        int index = (int)(Math.random() * dinnerList.size());
        return dinnerList.get(index);
    }
}
