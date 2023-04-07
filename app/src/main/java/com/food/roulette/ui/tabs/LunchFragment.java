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
import java.util.Collections;

public class LunchFragment extends Fragment {
    public LunchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ListView listView = rootView.findViewById(R.id.listView);

        ArrayList<String> listItems = this.getArguments().getStringArrayList("LunchItems");
        Collections.sort(listItems);

        ListViewAdapter adapter = new ListViewAdapter(getContext(), listItems);
        listView.setAdapter(adapter);

        return rootView;
    }
}
