package com.thedeveloperworldisyours.whichit.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.thedeveloperworldisyours.whichit.fragmanets.GridFragment;
import com.thedeveloperworldisyours.whichit.fragmanets.ListFragment;
import com.thedeveloperworldisyours.whichit.interfaces.UpdateableFragment;
import com.thedeveloperworldisyours.whichit.models.Instagram;
import com.thedeveloperworldisyours.whichit.utils.Constants;

/**
 * Created by javiergonzalezcabezas on 5/5/15.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    private Instagram mInstagram;

    public TabsAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
                return new GridFragment();

            case 1:
                return new ListFragment();
        }

        return null;
    }
    //call this method to update fragments in ViewPager dynamically
    public void update(Instagram instagram) {
        mInstagram = instagram;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateableFragment) {
            ((UpdateableFragment) object).update(mInstagram);
        }
        //don't return POSITION_NONE, avoid fragment recreation.
        return super.getItemPosition(object);
    }


    @Override
    public int getCount() {
        return Constants.TOTAL_TABS;
    }

}
