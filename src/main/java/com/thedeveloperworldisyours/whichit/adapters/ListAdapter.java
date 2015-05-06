package com.thedeveloperworldisyours.whichit.adapters;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.models.Datum;

import java.util.List;

import static com.thedeveloperworldisyours.whichit.R.layout.*;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class ListAdapter extends ArrayAdapter<Datum> {
    private final Context mContext;
    private final List<Datum> mValues;
    private int mType;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public ListAdapter(Context context, List<Datum> list, int type) {
        super(context, row_layout, list);
        this.mContext = context;
        this.mType = type;
        this.mValues = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_layout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.text.setMovementMethod(new ScrollingMovementMethod());
            viewHolder.image = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        switch (mType){
            case 0:
                //when is normal data
                holder.text.setText(mValues.get(position).getCaption().getText());
                Picasso.with(mContext).load(mValues.get(position).getImages().getLowResolution().getUrl()).into(holder.image);
                break;
            case 1:
                //when is user data
                holder.text.setText(mValues.get(position).getUsername());
                Picasso.with(mContext).load(mValues.get(position).getProfilePicture()).into(holder.image);
                break;
        }

        return rowView;
    }

}
