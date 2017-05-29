package com.syncsource.org.muzie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.fragments.GenreFragment;
import com.syncsource.org.muzie.fragments.MyScloudFragment;
import com.syncsource.org.muzie.fragments.MyYtubeFragment;
import com.syncsource.org.muzie.fragments.NewAndHotFragment;
import com.syncsource.org.muzie.fragments.TopFragment;

/**
 * Created by nls on 1/23/17.
 */

public class PagerAdapter extends SmartFragmentStatePagerAdapter {

    FragmentManager mFragmentManager;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TopFragment fragment = new TopFragment();
                return fragment;
            case 1:
                NewAndHotFragment newAndHotFragment = new NewAndHotFragment();
                return newAndHotFragment;
//            case 2:
//                GenreFragment genreFragment = new GenreFragment();
//                return genreFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
