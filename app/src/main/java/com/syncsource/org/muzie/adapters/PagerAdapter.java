package com.syncsource.org.muzie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.syncsource.org.muzie.fragments.MyScloudFragment;
import com.syncsource.org.muzie.fragments.MyYtubeFragment;

/**
 * Created by nls on 1/23/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfFragment;

    public PagerAdapter(FragmentManager fm, int numOfFragment) {
        super(fm);
        this.numOfFragment = numOfFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyYtubeFragment ytubeFragment = new MyYtubeFragment();
                return ytubeFragment;
            case 1:
                MyScloudFragment scloudFragment = MyScloudFragment.scNewInstance();
                return scloudFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfFragment;
    }
}
