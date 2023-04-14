package com.food.roulette.ui.home;

import android.icu.util.Calendar;

import com.food.roulette.ui.preferences.PreferencesHandler;


import java.util.ArrayList;

public class FoodMenuGenerator {

    enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    };

    PreferencesHandler preferencesHandler;
    public FoodMenuGenerator()
    {
        preferencesHandler = new PreferencesHandler();
    }

//    public boolean isWeekend()
//    {
//        Calendar calendar = Calendar.getInstance();
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//        if(dayOfWeek == Day.SATURDAY || )
//        return true;
//    }

    public String getBreakfast()
    {
        ArrayList<String> breakfastList = preferencesHandler.getBreakfastList();
        int index = (int)(Math.random() * breakfastList.size());
        return breakfastList.get(index);
    }

    public String getLunch()
    {
        ArrayList<String> lunchList = preferencesHandler.getLunchList();
        int index = (int)(Math.random() * lunchList.size());
        return lunchList.get(index);
    }

    public String getDinner()
    {
        ArrayList<String> dinnerList = preferencesHandler.getDinnerList();
        int index = (int)(Math.random() * dinnerList.size());
        return dinnerList.get(index);
    }
}
