package com.thedeveloperworldisyours.whichit.fragmanets;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thedeveloperworldisyours.whichit.MainActivity;
import com.thedeveloperworldisyours.whichit.R;
import com.thedeveloperworldisyours.whichit.adapters.ListAdapter;
import com.thedeveloperworldisyours.whichit.models.Datum;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;
import com.thedeveloperworldisyours.whichit.utils.Utils;
import com.thedeveloperworldisyours.whichit.webservice.Client;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListFragment extends Fragment {
    Instagram mInstagram;
    List<Datum> list;
    ListView listView;
    public ListFragment(Instagram instagram) {
        this.mInstagram = instagram;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };

         listView= (ListView) view.findViewById(R.id.fragment_list_list);

        if(Utils.isOnline(getActivity())){
            getInstagram();
        }

        return view;
    }

    public void getList(){

        ListAdapter listAdapter = new ListAdapter(getActivity(),list);
        listView.setAdapter(listAdapter);
    }
    public void getInstagram() {
        Callback<Instagram> callback = new Callback<Instagram>() {
            @Override
            public void success(Instagram instagram, Response response) {
                mInstagram= instagram;
                list = instagram.getData();
                getList();
                Log.v("getInstagram", "hola");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        Client.initRestAdapter().getInstagram(Constants.ID_INSTAGRAM,callback);
    }
}
