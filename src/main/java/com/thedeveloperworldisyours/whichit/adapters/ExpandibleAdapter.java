package com.thedeveloperworldisyours.whichit.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.thedeveloperworldisyours.whichit.R;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 6/5/15.
 */
public class ExpandibleAdapter extends CursorAdapter {

    private List<String> items;

    private TextView text;

    public ExpandibleAdapter(Context context, Cursor cursor, List<String> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        text.setText(items.get(cursor.getPosition()));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.expandible_item, parent, false);

        text = (TextView) view.findViewById(R.id.text);

        return view;

    }

}