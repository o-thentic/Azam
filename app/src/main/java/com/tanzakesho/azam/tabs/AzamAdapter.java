package com.tanzakesho.azam.tabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by othmankulindwa on 4/6/16.
 */
public class AzamAdapter extends ArrayAdapter<String> {

    public AzamAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public AzamAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setPadding(0, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        return view;

    }
}
