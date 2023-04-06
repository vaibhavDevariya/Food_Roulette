package com.food.roulette.ui.preferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.food.roulette.R;
import com.food.roulette.ui.tabs.BreakfastFragment;
import com.food.roulette.ui.tabs.DinnerFragment;
import com.food.roulette.ui.tabs.LunchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PreferencesFragment extends Fragment {

    PrefPagerAdapter PrefPagerAdapterObj;
    JSONObject userPref;
    Bundle bundle;
//    String[] BreakfastItems = {"Breakfast 1", "Breakfast 2", "Breakfast 3", "Breakfast 4", "Breakfast 5"};
//    String[] LunchItems = {"Lunch 1", "Lunch 2", "Lunch 3", "Lunch 4", "Lunch 5"};
//    String[] DinnerItems = {"Dinner 1", "Dinner 2", "Dinner 3", "Dinner 4", "Dinner 5"};

    public PreferencesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.pref_frag, container, false);

        // Initialize the ViewPager and TabLayout
        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        TabLayout mTabLayout = view.findViewById(R.id.tabLayout);

        userPref = getUserPref();
        bundle = new Bundle();
        bundle.putStringArray("BreakfastItems", getBreakfastItems() );
        bundle.putStringArray("LunchItems", getLunchItems() );
        bundle.putStringArray("DinnerItems", getDinnerItems() );


        // Set up the ViewPager with the adapter
        PrefPagerAdapterObj = new PrefPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(PrefPagerAdapterObj);
        // Link the TabLayout with the ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    public JSONObject getUserPref()
    {
        JSONObject json = null;
        try{
            File userJsonFile = new File(getActivity().getExternalFilesDir("userData"), "userFoodList.json");
            FileInputStream fileInputStream = new FileInputStream(userJsonFile);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            json = new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        }
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getBreakfastItems() {
        try {
            JSONArray breakFastList = userPref.getJSONArray("breakfast");
            String[] returnList = new String[breakFastList.length()];
            for (int i = 0; i < breakFastList.length(); i++)
                returnList[i] = breakFastList.getString(i);
            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public String[] getLunchItems() {
        try {

            JSONObject lunchObj = userPref.getJSONArray("lunch").getJSONObject(0);
            JSONArray noPrefArray = lunchObj.getJSONArray("noPref");
            JSONArray weekendArray = lunchObj.getJSONArray("weekend");

            String[] returnList = new String[noPrefArray.length()+weekendArray.length()];

            for (int i = 0; i < returnList.length; i++) {
                if (i < noPrefArray.length()) {
                    returnList[i] = noPrefArray.getString(i);
                } else {
                    returnList[i] = weekendArray.getString(i - noPrefArray.length());
                }
            }

            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public String[] getDinnerItems() {
        try {
            JSONArray dinnerListNoPref = userPref.getJSONArray("dinner").getJSONObject(0).getJSONArray("noPref");
            JSONArray dinnerListWeekend = userPref.getJSONArray("dinner").getJSONObject(0).getJSONArray("weekend");
            String[] returnList = new String[dinnerListNoPref.length()+dinnerListWeekend.length()];
            for (int i = 0; i < dinnerListNoPref.length(); i++)
                returnList[i] = dinnerListNoPref.getString(i);
            for (int i = 0; i < dinnerListWeekend.length(); i++)
                returnList[i+dinnerListNoPref.length()] = dinnerListWeekend.getString(i);
            return returnList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private class PrefPagerAdapter extends FragmentPagerAdapter
    {

        public PrefPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BreakfastFragment breakfastFragment = new BreakfastFragment();
                    breakfastFragment.setArguments(bundle);
                    return breakfastFragment;
                case 1:
                    LunchFragment lunchFragment = new LunchFragment();
                    lunchFragment.setArguments(bundle);
                    return lunchFragment;
                case 2:
                    DinnerFragment dinnerFragment = new DinnerFragment();
                    dinnerFragment.setArguments(bundle);
                    return dinnerFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Breakfast";
                case 1:
                    return "Lunch";
                case 2:
                    return "Dinner";
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
