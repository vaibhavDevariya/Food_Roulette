package com.food.roulette.ui.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.food.roulette.R;
import com.food.roulette.ui.preferences.PreferencesHandler;

import java.util.ArrayList;

public class DinnerFragment extends Fragment {
    public DinnerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ListView listView = rootView.findViewById(R.id.listView);

        ArrayList<String> listItems = this.getArguments().getStringArrayList("DinnerItems");

        ListViewAdapter adapter = new ListViewAdapter(getContext(), listItems);
        listView.setAdapter(adapter);


//        String[] listItems = this.getArguments().getStringArray("DinnerItems");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, listItems);
//        listView.setAdapter(adapter);
        return rootView;
    }
}
