package com.thedeveloperworldisyours.whichit.webservice;

import com.squareup.okhttp.OkHttpClient;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class Client {

    public interface ClientInterface{
        @GET("/nofilter/media/recent")
        void getInstagram( @Query("client_id") String clientId, Callback<Instagram> callback);
    }

    public static ClientInterface initRestAdapter()
    {
        OkHttpClient client = new OkHttpClient();

        return (ClientInterface) new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(Constants.URL)
                .build()
                .create(ClientInterface.class);
    }
}
