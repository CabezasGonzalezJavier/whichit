package com.thedeveloperworldisyours.whichit.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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


        // create a new ImageView for each item referenced by the Adapter
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

            switch (mType){
                case 0:
                    //when is normal data
                    Picasso.with(mActivity).load(mValues.get(position).getImages().getLowResolution().getUrl()).into(imageView);
                    break;
                case 1:
                    //when is user data
                    Picasso.with(mActivity).load(mValues.get(position).getProfilePicture()).into(imageView);
                    break;
            }

            return imageView;
        }
}
