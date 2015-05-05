package com.thedeveloperworldisyours.whichit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.models.Datum;
import com.thedeveloperworldisyours.whichit.models.Instagram;

import java.util.List;

import static com.thedeveloperworldisyours.whichit.R.layout.*;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class ListAdapter extends ArrayAdapter <Instagram> {
    private final Context context;
    private final List<Datum> values;


    public ListAdapter(Context context, List<Datum> list) {
        super(context,row_layout,list);
        this.context = context;
        this.values = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Picasso.with(context).load(values.get(position).getLink()).into(imageView);

        return rowView;
    }
}
