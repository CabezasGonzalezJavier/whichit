package com.thedeveloperworldisyours.whichit.fragmanets;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListFragment extends Fragment implements UpdateableFragment, SwipeRefreshLayout.OnRefreshListener {

    ListView mListView;
    private Instagram mInstagram;
    ListAdapter mListAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog mProgressDialog;
    private boolean mFinishScroll = false;
    private List<Datum> mList;
    private int mType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListView = (ListView) view.findViewById(R.id.fragment_list_list);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mList = new ArrayList<Datum>();
        mListView.setOnScrollListener(new InfiniteScrollListener(5) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                mProgressDialog.show();
                getInfo();

                mFinishScroll = true;
            }
        });

//        getList();
        return view;
    }

    @Override
    public void update(Instagram instagram,int type) {
        mInstagram = instagram;
        mType = type;
        mList = new ArrayList<Datum>();
        addList(instagram.getData());
        buildList();
    }

    @Override
    public void onRefresh() {
        mListAdapter.clear();
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

    public void buildList() {
        if (!mFinishScroll) {
            mListAdapter = new ListAdapter(getActivity(), mList,mType);
            mListView.setAdapter(mListAdapter);

        } else {
            mListAdapter.notifyDataSetChanged();
            mFinishScroll = false;
            mProgressDialog.dismiss();
        }
    }

}
