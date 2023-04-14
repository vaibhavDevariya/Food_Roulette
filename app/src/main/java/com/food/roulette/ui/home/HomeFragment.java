package com.food.roulette.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.food.roulette.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);

        Button button = view.findViewById(R.id.generateButton);
        button.setOnClickListener(v -> {
            TextView breakfastText = view.findViewById(R.id.breakfastText);
            TextView lunchText = view.findViewById(R.id.lunchText);
            TextView dinnerText = view.findViewById(R.id.dinnerText);

            FoodMenuGenerator foodMenuGenerator = new FoodMenuGenerator();

            breakfastText.setText("Breakfast : " + foodMenuGenerator.getBreakfast());
            lunchText.setText("Lunch : " + foodMenuGenerator.getLunch());
            dinnerText.setText("Dinner : " + foodMenuGenerator.getDinner());

        });
        return view;
    }
}
