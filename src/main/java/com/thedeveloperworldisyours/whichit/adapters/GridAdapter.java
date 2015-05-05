package com.thedeveloperworldisyours.whichit.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.thedeveloperworldisyours.whichit.R;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class GridAdapter extends ArrayAdapter<String> {
    private Activity mActivity;
    private final List<String> mValues;

    public GridAdapter(Activity activity, List<String> vaules) {
        super(activity, R.layout.fragment_grid, vaules);
        this.mActivity = activity;
        this.mValues = vaules;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mActivity);
            imageView.setLayoutParams(new GridView.LayoutParams(385, 285));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackground(mActivity.getResources().getDrawable(android.R.drawable.ic_dialog_info));
        } else {
            imageView = (ImageView) convertView;
        }

//        Picasso.with(mActivity).load(mValues.get(position)).into(imageView);
        return imageView;
    }
}
