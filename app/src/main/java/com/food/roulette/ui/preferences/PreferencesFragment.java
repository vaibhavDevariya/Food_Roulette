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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.food.roulette.R;
import com.food.roulette.ui.tabs.BreakfastFragment;
import com.food.roulette.ui.tabs.DinnerFragment;
import com.food.roulette.ui.tabs.LunchFragment;

public class PreferencesFragment extends Fragment {
    Bundle bundle;


    public PreferencesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.pref_frag, container, false);

        PreferencesHandler preferencesHandler = new PreferencesHandler(getContext());

        // Initialize the ViewPager, TabLayout and floating add button
        ViewPager mViewPager = view.findViewById(R.id.viewPager);
        TabLayout mTabLayout = view.findViewById(R.id.tabLayout);
        FloatingActionButton fab = view.findViewById(R.id.fab);


        // Set up the ViewPager with the adapter
        PrefPagerAdapter prefPagerAdapter = new PrefPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(prefPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        bundle = new Bundle();
        bundle.putStringArrayList("BreakfastItems", preferencesHandler.getBreakfastList() );
        bundle.putStringArrayList("LunchItems", preferencesHandler.getLunchList() );
        bundle.putStringArrayList("DinnerItems", preferencesHandler.getDinnerList() );


        fab.setOnClickListener(view1 -> {
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

                if(!foodItem.isEmpty() && !foodTime.isEmpty()) {
                    preferencesHandler.addItem(foodItem, foodTime, pref);
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

//        public void setDeletionListener(DeletionListener listener) {
//            this.deletionListener = listener;
//        }
//
//        public interface DeletionListener {
//            void onItemDeleted();
//        }

    }
}
