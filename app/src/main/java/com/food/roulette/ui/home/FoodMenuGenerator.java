package com.food.roulette.ui.home;

import android.icu.util.Calendar;
import android.util.Log;

import com.food.roulette.ui.preferences.PreferencesHandler;


import java.util.ArrayList;
import java.util.Random;

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

    private static String ProbabilityBasedRandomItem(ArrayList<String> a, ArrayList<String> b, double probability) {
        Random random = new Random();
        double randomValue = random.nextDouble(); // Generate a random value between 0.0 and 1.0

        if (randomValue <= probability) {
            int randomIndex = random.nextInt(a.size());
            return a.get(randomIndex).replace("_w","");
        } else {
            int randomIndex = random.nextInt(b.size());
            return b.get(randomIndex).replace("_w","");
        }
    }

    public String getBreakfast()
    {
        ArrayList<String> breakfastList =
                PreferencesHandler.getInstance().getListFOR(PreferencesHandler.TIME.BREAKFAST, PreferencesHandler.DAY.ALL) ;
        int index = (int)(Math.random() * breakfastList.size());
        return breakfastList.get(index);
    }

    public String getLunch()
    {
        ArrayList<String> weekdayList = PreferencesHandler.getInstance().getListFOR(PreferencesHandler.TIME.LUNCH,
                PreferencesHandler.DAY.WEEKDAY) ;
        ArrayList<String> weekendList = PreferencesHandler.getInstance().getListFOR(PreferencesHandler.TIME.LUNCH,
                PreferencesHandler.DAY.WEEKEND);

        return isWeekend() ? ProbabilityBasedRandomItem(weekendList,weekdayList,0.8) :
                ProbabilityBasedRandomItem(weekendList,weekdayList,0.05);
    }

    public String getDinner()
    {
        ArrayList<String> weekdayList = PreferencesHandler.getInstance().getListFOR(PreferencesHandler.TIME.DINNER,
                PreferencesHandler.DAY.WEEKDAY) ;
        ArrayList<String> weekendList = PreferencesHandler.getInstance().getListFOR(PreferencesHandler.TIME.DINNER,
                PreferencesHandler.DAY.WEEKEND);

        return isWeekend() ? ProbabilityBasedRandomItem(weekendList,weekdayList,0.8) :
                ProbabilityBasedRandomItem(weekendList,weekdayList,0.05);
    }
}
