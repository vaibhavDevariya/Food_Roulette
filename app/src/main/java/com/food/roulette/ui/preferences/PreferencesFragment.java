package com.food.roulette.ui.preferences;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.food.roulette.ui.tabs.ListViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.food.roulette.R;
import com.food.roulette.ui.tabs.BreakfastFragment;
import com.food.roulette.ui.tabs.DinnerFragment;
import com.food.roulette.ui.tabs.LunchFragment;

import java.util.ArrayList;

public class PreferencesFragment extends Fragment {

    public PreferencesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.pref_frag, container, false);

        // Initialize the ViewPager, TabLayout and floating add button
        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        TabLayout mTabLayout = view.findViewById(R.id.tabLayout);
        FloatingActionButton addButton = view.findViewById(R.id.addButton);


        // Set up the ViewPager with the adapter
        PrefPagerAdapter prefPagerAdapter = new PrefPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(prefPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        addButton.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = requireActivity().getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.add_dialog, null);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();

            Spinner dropdown = dialogView.findViewById(R.id.dropdown);
            RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
            EditText editText = dialogView.findViewById(R.id.editText);
            Button buttonOk = dialogView.findViewById(R.id.buttonOk);
            Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(v -> dialog.dismiss());

            buttonOk.setOnClickListener(v -> {
                String foodItem = editText.getText().toString();
                String foodTime = dropdown.getSelectedItem().toString().toLowerCase();
                String pref = ((RadioButton)dialogView.findViewById(radioGroup.getCheckedRadioButtonId())).
                        getText().toString().toLowerCase();

                if(!foodItem.isEmpty() && !foodTime.isEmpty())
                {
                    PreferencesHandler.getInstance().addItem(foodItem, foodTime, pref);

                    if(foodTime.equalsIgnoreCase("Breakfast"))
                        BreakfastFragment.adapter.addItem();
                    else if(foodTime.equalsIgnoreCase("Lunch"))
                        LunchFragment.adapter.addItem();
                    else if(foodTime.equalsIgnoreCase("Dinner"))
                        DinnerFragment.adapter.addItem();

                    dialog.dismiss();
                }
            });

            dialog.show();
        });

        return view;
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
                    return new BreakfastFragment();
                case 1:
                    return new LunchFragment();
                case 2:
                    return new DinnerFragment();
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
