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
import java.util.Collections;


public class BreakfastFragment extends Fragment {

    ListViewAdapter adapter;
    public BreakfastFragment() {
    }

    public interface OnListItemDeletedListener {
        void onListItemDeleted(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ListView listView = rootView.findViewById(R.id.listView);

        ArrayList<String> listItems = this.getArguments().getStringArrayList("BreakfastItems");
        Collections.sort(listItems);

        adapter = new ListViewAdapter(getContext(), listItems);
        listView.setAdapter(adapter);

        return rootView;
    }

    public void refreshListView() {
        adapter.notifyDataSetChanged();
    }
}
