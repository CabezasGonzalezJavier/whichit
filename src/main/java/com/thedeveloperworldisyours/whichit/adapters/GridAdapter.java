package com.thedeveloperworldisyours.whichit.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.models.Datum;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class GridAdapter extends ArrayAdapter<Datum> {
    private Activity mActivity;
    private final List<Datum> mValues;
    private int mType;

    public GridAdapter(Activity activity, List<Datum> vaules, int type) {
        super(activity, R.layout.fragment_grid, vaules);
        this.mActivity = activity;
        this.mValues = vaules;
        this.mType = type;
    }

    static class ViewHolder {
        public ImageView image;
    }

    /**
     * create a new ImageView for each item referenced by the Adapter
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_grid, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.row_grid_icon);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        switch (mType) {
            case 0:
                //when is normal data
                Picasso.with(mActivity).load(mValues.get(position).getImages().getLowResolution().getUrl()).into(holder.image);
                break;
            case 1:
                //when is user data
                Picasso.with(mActivity).load(mValues.get(position).getProfilePicture()).into(holder.image);
                break;
        }

        return rowView;
    }
}
