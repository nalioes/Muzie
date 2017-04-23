package com.syncsource.org.muzie.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.ActivityYouTubeBinding;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.fragments.MyYtubeFragment;
import com.syncsource.org.muzie.model.MyTrack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class YouTubeActivity extends BaseActivity implements MyYtubeFragment.TrackIntetface {

    ActivityYouTubeBinding binding;
    List<MyTrack> myTracks = new ArrayList<>();

    public static Intent intentInstance(Context context) {
        Intent intent = new Intent(context, YouTubeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_you_tube);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("YouTube");
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.chevron_left));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void myTrackEvent(TrackEvent.OnTrackSyncEvent event) {
        if (event.isSuccess()) {
            myTracks = event.getMyTrack();

            MyYtubeFragment fragment = new MyYtubeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_youtube, fragment).commit();
        }
    }

    @Override
    public List<MyTrack> getMyTrack() {
        if (myTracks.size() > 0) {
            return myTracks;
        } else {
            return null;
        }
    }
}
