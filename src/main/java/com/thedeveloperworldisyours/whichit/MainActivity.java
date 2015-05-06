package com.thedeveloperworldisyours.whichit;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.thedeveloperworldisyours.whichit.adapters.TabsAdapter;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;
import com.thedeveloperworldisyours.whichit.utils.Utils;
import com.thedeveloperworldisyours.whichit.webservice.Client;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements  android.support.v7.app.ActionBar.TabListener {


    private ViewPager tabsviewPager;
    private ActionBar mActionBar;
    private TabsAdapter mTabsAdapter;
    private Instagram mInstagram;
    private SearchView mSearchView;
    private TextView mStatusView;
    private String userString;
    private int mSearchType=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        mInstagram = (Instagram)intent.getSerializableExtra(Constants.ID_INTENT);
        if(Utils.isOnline(MainActivity.this)){
            getInstagram();
        }else {
            Toast.makeText(MainActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
        }

        createTabBar();
    }


    public void createTabBar(){
        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);

        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsviewPager.setAdapter(mTabsAdapter);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab gridTab = getSupportActionBar().newTab().setText(Constants.FIRST_TAB).setTabListener(this);
        ActionBar.Tab listTab = getSupportActionBar().newTab().setText(Constants.SECOND_TAB).setTabListener(this);

        getSupportActionBar().addTab(gridTab);
        getSupportActionBar().addTab(listTab);

        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onTabReselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab selectedtab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String entryString) {
                userString = entryString;
                String word = "@";

                if(entryString.substring(0, 1).equals(word)){
                    mSearchType=1;
                    getUser();
                }else{
                    getUser();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {


                return true;

            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get data from instagram
     */
    public void getInstagram() {
        Callback<Instagram> callback = new Callback<Instagram>() {
            @Override
            public void success(Instagram instagram, Response response) {
                mInstagram= instagram;
                mTabsAdapter.update(mInstagram,0);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
            }
        };
        Client.initRestAdapter().getInstagram(Constants.ID_INSTAGRAM,callback);
    }

    /**
     * Get user data from instagram
     */
    public void getUser(){
        Callback<Instagram> callback = new Callback<Instagram>() {
            @Override
            public void success(Instagram instagram, Response response) {
                mInstagram= instagram;
                diferentSearch();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
            }
        };
        Client.initRestAdapter().getUser(userString,Constants.CLIENT_ID, callback);
    }

    public void diferentSearch(){
        switch (mSearchType){
            case 0:
                mTabsAdapter.update(mInstagram,1);
                break;
            case 1:
                getUserPath(mInstagram.getData().get(0).getId());
                mSearchType = 0;
                break;
        }
    }

    /**
     * Get data about user from instagram
     */
    public void getUserPath(String userPath){
        Callback<Instagram> callback = new Callback<Instagram>() {
            @Override
            public void success(Instagram instagram, Response response) {
                mInstagram= instagram;
                mTabsAdapter.update(mInstagram,0);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
            }
        };
        Client.initRestAdapter().getUserPath(userPath,Constants.CLIENT_ID,callback);
    }

}
