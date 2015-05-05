package com.thedeveloperworldisyours.whichit.fragmanets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.thedeveloperworldisyours.whichit.MainActivity;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.models.Instagram;


public class GridFragment extends Fragment {

    private GridView mGridView;
    Instagram mInstagram;
    public GridFragment(Instagram instagram) {
        this.mInstagram = instagram;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.fragment_grid_gridView);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


            }
        });

        return view;
    }


}
