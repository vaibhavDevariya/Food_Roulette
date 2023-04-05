package com.whattoeat.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.whattoeat.app.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);

//        // Access the text fields by their IDs
//        EditText editText1 = view.findViewById(R.id.editText1);
//        EditText editText2 = view.findViewById(R.id.editText2);
//        EditText editText3 = view.findViewById(R.id.editText3);

        // Set an OnClickListener for the button
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String breakfast = view.findViewById(R.id.breakfastText).toString();
                String lunch = view.findViewById(R.id.lunchText).toString();
                String dinner = view.findViewById(R.id.dinnerText).toString();
            }
        });

        return view;
    }
}
