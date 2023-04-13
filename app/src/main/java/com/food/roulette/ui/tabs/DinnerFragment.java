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

public class DinnerFragment extends Fragment implements ListViewAdapter.OnDeleteClickListener{

    ArrayList<String> listItems;
    ListViewAdapter adapter;

    public DinnerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ListView listView = rootView.findViewById(R.id.listView);

        listItems = this.getArguments().getStringArrayList("DinnerItems");
//        Collections.sort(listItems);

        adapter = new ListViewAdapter(getContext(), listItems);
        adapter.setOnDeleteClickListener(this);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDeleteClick(int itemPosition) {
//        list.remove(position);
//        adapter.notifyDataSetChanged();
        new PreferencesHandler().deleteItem("dinner",itemPosition,listItems.get(itemPosition));
    }

    public void onItemAdded()
    {
        adapter.notifyDataSetChanged();
    }
}
