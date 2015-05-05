package com.thedeveloperworldisyours.whichit.adapters;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.thedeveloperworldisyours.whichit.MainActivity;
import com.thedeveloperworldisyours.whichit.fragmanets.GridFragment;
import com.thedeveloperworldisyours.whichit.fragmanets.ListFragment;
import com.thedeveloperworldisyours.whichit.models.Instagram;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    private int TOTAL_TABS = 2;
    private Instagram mInstagram;

    public TabsAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    public TabsAdapter(FragmentManager fm, int TOTAL_TABS,Instagram instagram) {
        super(fm);
        this.TOTAL_TABS = TOTAL_TABS;
        mInstagram=instagram;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
                return new GridFragment(mInstagram);

            case 1:
                return new ListFragment(mInstagram);

        }

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}
