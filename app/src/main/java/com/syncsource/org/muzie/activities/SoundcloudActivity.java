package com.syncsource.org.muzie.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.FilterAdapter;
import com.syncsource.org.muzie.adapters.PagerAdapter;
import com.syncsource.org.muzie.databinding.ActivitySoundcloudBinding;
import com.syncsource.org.muzie.databinding.ItemFilterBinding;
import com.syncsource.org.muzie.databinding.ViewFilterGenreBinding;
import com.syncsource.org.muzie.databinding.ViewPlayingTrackBinding;
import com.syncsource.org.muzie.fragments.NewAndHotFragment;
import com.syncsource.org.muzie.fragments.TopFragment;
import com.syncsource.org.muzie.model.Filter;
import com.syncsource.org.muzie.views.MusicBarView;

import java.util.ArrayList;
import java.util.List;


public class SoundcloudActivity extends AppCompatActivity {
    ActivitySoundcloudBinding binding;
    AlertDialog.Builder builder;
    AlertDialog filterDialog;
    PagerAdapter pagerAdapter;
    private Filter filterGeneres;
    private Filter topFilter;
    private Filter newHotFilter;
    private boolean isSelectedTop = false;
    private boolean isSelectedNewHot = false;
    private boolean isFiltered = false;
    int itemPosition = 0;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.chevron_left));
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        builder = new AlertDialog.Builder(SoundcloudActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
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
                if (position == 0) {
                    isSelectedTop = true;
                    isSelectedNewHot = false;
                    if (filterGeneres != null) {
                        if (topFilter == null) {
                            TopFragment topFragment = (TopFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                            topFragment.setData(filterGeneres);
                            topFilter = filterGeneres;
                        } else {
                            if (!topFilter.getName().equals(filterGeneres.getName())) {
                                TopFragment topFragment = (TopFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                                topFragment.setData(filterGeneres);
                                topFilter = filterGeneres;
                            }
                        }
                    }
                } else if (position == 1) {
                    isSelectedNewHot = true;
                    isSelectedTop = false;
                    if (filterGeneres != null) {
                        if (newHotFilter == null) {
                            NewAndHotFragment newAndHotFragment = (NewAndHotFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                            newAndHotFragment.setData(filterGeneres);
                            newHotFilter = filterGeneres;
                        } else {
                            if (!newHotFilter.getName().equals(filterGeneres.getName())) {
                                NewAndHotFragment newAndHotFragment = (NewAndHotFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                                newAndHotFragment.setData(filterGeneres);
                                newHotFilter = filterGeneres;
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (binding.viewPager.getCurrentItem() == 0) {
            isSelectedTop = true;
            isSelectedNewHot = false;
            binding.tabLayout.getTabAt(0).setText("Top 50");
            binding.tabLayout.getTabAt(1).setText("New & Hot");
        }
        binding.filterGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewFilterGenreBinding filterBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.view_filter_genre, null, false);
                filterBinding.genreRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                final FilterAdapter filterAdapter = new FilterAdapter(getApplicationContext());

                if (itemPosition > 0) {
                    filterBinding.genreRecycle.scrollToPosition(itemPosition);
                } else {
                    filterBinding.genreRecycle.scrollToPosition(0);
                }

                filterAdapter.addItem(getGenreChart(), new FilterAdapter.filterListener() {
                    @Override
                    public void isChecked(Boolean check, int position, Filter filter) {
                        if (check) {
                            isFiltered = true;
                            filterGeneres = filter;
                            filterAdapter.notifyDataSetChanged();
                            itemPosition = position;
                            filterDialog.dismiss();
                            binding.genresTitle.setText(filter.getName());
                            binding.genresTitle.setTextColor(getResources().getColor(R.color.my_color_2));
                            binding.img.setImageDrawable(getResources().getDrawable(R.drawable.menu_down_check));
                            if (isSelectedTop) {
                                topFilter = filter;
                                TopFragment topFragment = (TopFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                                topFragment.setData(filter);
                            } else {
                                newHotFilter = filter;
                                NewAndHotFragment newAndHotFragment = (NewAndHotFragment) pagerAdapter.getRegisteredFragment(binding.viewPager.getCurrentItem());
                                newAndHotFragment.setData(filter);
                            }
                        }
                    }
                }, itemPosition);

                filterBinding.genreRecycle.setHasFixedSize(true);
                filterBinding.genreRecycle.setAdapter(filterAdapter);
                filterDialog = builder.create();
                filterDialog.setView(filterBinding.getRoot());
                filterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_dark_dialog_background)));
                filterDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = filterDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                filterDialog.show();
            }
        });
        new MusicBarView().displayPlayBar(SoundcloudActivity.this, getApplicationContext(), binding.barContainer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Filter> getGenreChart() {
        String[] genreArray = getApplicationContext().getResources().getStringArray(R.array.music_genres);
        String[] genreValArray = getApplicationContext().getResources().getStringArray(R.array.music_genres_value);
        List<Filter> genreFilter = new ArrayList<>();
        for (int index = 0; index < genreArray.length; index++) {
            genreFilter.add(new Filter(genreArray[index], genreValArray[index]));
        }
        return genreFilter;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
