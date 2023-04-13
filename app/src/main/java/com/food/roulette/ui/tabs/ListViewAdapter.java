package com.food.roulette.ui.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.food.roulette.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<String> items;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public ListViewAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_view, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.deleteButton = view.findViewById(R.id.deleteButton);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String item = items.get(position);
        viewHolder.textView.setText(item);
        viewHolder.deleteButton.setTag(position);
        viewHolder.deleteButton.setOnClickListener(v -> {
            int position1 = (Integer) v.getTag();
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(position1);
            }
            items.remove(position1);
            notifyDataSetChanged();
        });

        return view;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int itemPosition);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }

    static class ViewHolder {
        TextView textView;
        Button deleteButton;
    }
}
