package com.syncsource.org.muzie;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.syncsource.org.muzie.databinding.ActivityTopSoundCloudBinding;

public class TopSoundCloudActivity extends AppCompatActivity {

    public static final String GENRE_TYPE = "genre_type";
    public static final String FILTER_TYPE = "filter_type";
    public static final String FILTER_NAME = "filter_name";
    private static boolean isGenre = false;
    private ActivityTopSoundCloudBinding binding;
    public static String title;

    public static Intent getIntentNewInstance(Context context, String kind) {
        Intent intent = new Intent(context, TopSoundCloudActivity.class);
        isGenre = false;
        title = "New and Hot";
        intent.putExtra(FILTER_TYPE, kind);
        return intent;
    }

    public static Intent getIntentNewObj(Context context, String kind, String genreName, String queryGenre) {
        Intent intent = new Intent(context, TopSoundCloudActivity.class);
        isGenre = true;
        title = genreName;
        intent.putExtra(FILTER_TYPE, kind);
        intent.putExtra(FILTER_NAME, queryGenre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_sound_cloud);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
