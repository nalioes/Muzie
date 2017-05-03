package com.syncsource.org.muzie.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.PagerAdapter;
import com.syncsource.org.muzie.databinding.ActivitySoundcloudBinding;

public class SoundcloudActivity extends AppCompatActivity {
    ActivitySoundcloudBinding binding;

    PagerAdapter pagerAdapter;

    public static Intent intentInstance(Context context) {
        Intent intent = new Intent(context, SoundcloudActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_soundcloud);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setTitle("SoundCloud");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (binding.viewPager.getCurrentItem() == 0) {
            binding.tabLayout.getTabAt(0).setText("Top(50)");
            binding.tabLayout.getTabAt(1).setText("New & Hot");
            binding.tabLayout.getTabAt(2).setText("All genre");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
