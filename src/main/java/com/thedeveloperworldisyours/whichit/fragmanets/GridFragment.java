package com.thedeveloperworldisyours.whichit.fragmanets;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.adapters.GridAdapter;
import com.thedeveloperworldisyours.whichit.interfaces.UpdateableFragment;
import com.thedeveloperworldisyours.whichit.listener.InfiniteScrollListener;
import com.thedeveloperworldisyours.whichit.models.Datum;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;
import com.thedeveloperworldisyours.whichit.utils.Utils;
import com.thedeveloperworldisyours.whichit.webservice.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GridFragment extends Fragment implements UpdateableFragment, SwipeRefreshLayout.OnRefreshListener {

    private Point mPoint;
    private GridView mGridView;
    GridAdapter mGridAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog mProgressDialog;
    Instagram mInstagram;
    private List<Datum> mList;
    private int mType;

    private boolean mFinishScroll = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.fragment_grid_gridView);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mList = new ArrayList<Datum>();
        int[] location = new int[2];
        mGridView.getLocationOnScreen(location);
        mPoint = new Point();
        mPoint.x = location[0];
        mPoint.y = location[1];


        mProgressDialog = new ProgressDialog(getActivity());
        mGridView.setOnScrollListener(new InfiniteScrollListener(1) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                mProgressDialog.show();
                getInfo();

                mFinishScroll = true;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                showPopup(getActivity(),mPoint,position);
//                showAlertDialog(position);
            }
        });

        return view;
    }

    @Override
    public void update(Instagram instagram, int type) {
        mInstagram = instagram;
        mType = type;
        mList = new ArrayList<Datum>();
        addList(instagram.getData());
        buildList();
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
                mType = 0;
                addList(instagram.getData());
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
        dialog.setContentView(R.layout.row_layout);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.label);
//        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.icon);
        switch (mType){
            case 0:
                text.setText(mInstagram.getData().get(position).getCaption().getText());
                Picasso.with(getActivity()).load(mInstagram.getData().get(position).getImages().getLowResolution().getUrl()).into(image);
                break;
            case 1:
                text.setText(mInstagram.getData().get(position).getUsername());
                Picasso.with(getActivity()).load(mInstagram.getData().get(position).getProfilePicture()).into(image);
                break;
            case 2:
                break;
        }
        dialog.show();
    }

    public void buildList() {
        if (!mFinishScroll) {
            mGridAdapter = new GridAdapter(getActivity(), mList,mType);
            mGridView.setAdapter(mGridAdapter);

        } else {
            mGridAdapter.notifyDataSetChanged();
            mFinishScroll = false;
            mProgressDialog.dismiss();
        }
    }



    private void showPopup(final Activity context, Point p, int position) {

        Rect rectgle= new Rect();
        Window window= getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight= rectgle.top;

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int popupWidth = display.getWidth();
        int popupHeight = 550;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context
                .findViewById(R.id.row_layout_linear_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.row_layout, viewGroup,false);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down,
        // relative to button's position.

        int OFFSET_X = 0;
        int OFFSET_Y = 0;
        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER_VERTICAL, p.x + OFFSET_X, p.y
                + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when
        // clicked.


        TextView text = (TextView) layout.findViewById(R.id.label);
        text.setMovementMethod(new ScrollingMovementMethod());
//        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) layout.findViewById(R.id.icon);
        switch (mType){
            case 0:
                text.setText(mList.get(position).getCaption().getText());
                Picasso.with(getActivity()).load(mList.get(position).getImages().getLowResolution().getUrl()).into(image);
                break;
            case 1:
                text.setText(mList.get(position).getUsername());
                Picasso.with(getActivity()).load(mList.get(position).getProfilePicture()).into(image);
                break;
            case 2:
                break;
        }
    }
}
