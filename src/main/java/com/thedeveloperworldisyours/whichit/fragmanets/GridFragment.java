package com.thedeveloperworldisyours.whichit.fragmanets;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.whichit.MainActivity;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.adapters.GridAdapter;
import com.thedeveloperworldisyours.whichit.adapters.ListAdapter;
import com.thedeveloperworldisyours.whichit.interfaces.UpdateableFragment;
import com.thedeveloperworldisyours.whichit.listener.InfiniteScrollListener;
import com.thedeveloperworldisyours.whichit.models.Datum;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;
import com.thedeveloperworldisyours.whichit.utils.Utils;
import com.thedeveloperworldisyours.whichit.webservice.Client;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GridFragment extends Fragment implements UpdateableFragment, SwipeRefreshLayout.OnRefreshListener {

    private GridView mGridView;
    GridAdapter mGridAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog mProgressDialog;
    Instagram mInstagram;
    private List<Datum> mList;

    private boolean mFinishScroll = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.fragment_grid_gridView);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_container);
        mSwipeLayout.setOnRefreshListener(this);

        mProgressDialog = new ProgressDialog(getActivity());
        mGridView.setOnScrollListener(new InfiniteScrollListener(5) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                mProgressDialog.show();
                getInfo();
                addList(mList);
                mFinishScroll = true;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                showAlertDialog(position);
            }
        });

        return view;
    }
    public void createGridView(){
        mGridAdapter = new GridAdapter(getActivity(),mInstagram.getData());
        mGridView.setAdapter(mGridAdapter);
    }


    @Override
    public void update(Instagram instagram) {
        mInstagram = instagram;
        createGridView();
    }

    @Override
    public void onRefresh() {
        mGridAdapter.clear();
        getInfo();
        mSwipeLayout.setRefreshing(false);
    }

    public void getInfo(){
        if(Utils.isOnline(getActivity())){
            getInstagram();
        }else {
            Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
        }
    }

    public void getInstagram() {
        Callback<Instagram> callback = new Callback<Instagram>() {
            @Override
            public void success(Instagram instagram, Response response) {
                mList = instagram.getData();
                buildList();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),R.string.error_data,Toast.LENGTH_SHORT).show();
            }
        };
        Client.initRestAdapter().getInstagram(Constants.ID_INSTAGRAM,callback);
    }

    public void addList(List<Datum> mValues){
        for (int i=0; i <mValues.size();i++){
            mList.add(mValues.get(i));
        }
    }


    public void showAlertDialog(int position){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.alert_dialog);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Picasso.with(getActivity()).load(mInstagram.getData().get(position).getImages().getLowResolution().getUrl()).into(image);
        dialog.show();
    }

    public void buildList() {
        if (!mFinishScroll) {
            mGridAdapter = new GridAdapter(getActivity(), mList);
            mGridView.setAdapter(mGridAdapter);

        } else {
            mGridAdapter.notifyDataSetChanged();
            mFinishScroll = false;
            mProgressDialog.dismiss();
        }
    }
}
