package com.food.roulette.ui.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.food.roulette.R;
import com.food.roulette.ui.preferences.PreferencesHandler;

import java.util.ArrayList;

public class BreakfastFragment extends Fragment {
    public BreakfastFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ListView listView = rootView.findViewById(R.id.listView);

//        Log.d("preferencesHandler.getBreakfastList()", "onCreateView: "+preferencesHandler.getBreakfastList());

        ArrayList<String> listItems = this.getArguments().getStringArrayList("BreakfastItems");

        ListViewAdapter adapter = new ListViewAdapter(getContext(), listItems);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Get the selected item position
//                int itemPosition = position;
//                // Pass the selected item position to FragmentB or FragmentC
//            }
//        });

        return rootView;
    }
}
