package com.grieferpig.papertask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private final int resourceId;

    public ListAdapter(Context context, int textViewResourceId, List<TaskItem> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItem item = (TaskItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(this.resourceId, (ViewGroup) null);
        ((ImageView) view.findViewById(R.id.navigate)).setImageResource(R.mipmap.goto_logo);
        ((TextView) view.findViewById(R.id.title)).setText(item.getTitle());
        ((TextView) view.findViewById(R.id.subtitle)).setText(item.getSubTitle());
        return view;
    }
}
