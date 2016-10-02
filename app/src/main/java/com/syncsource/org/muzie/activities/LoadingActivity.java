package com.syncsource.org.muzie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.MostTrackItem;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;
import com.syncsource.org.muzie.rests.ApiClient;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private ApiClient apiClient;
    private List<MostTrackItem> snippetItems = new ArrayList<>();
    private String token;
    private List<TrackItem> trackItems = new ArrayList<>();
    private List<MyTrack> myTrackList;
    private LinearLayout errorLayout;
    private Button reloadButton;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        reloadButton = (Button) findViewById(R.id.reload);
        progress = (ProgressBar) findViewById(R.id.load_more);
        apiClient = ApiClient.getApiClientInstance();
        errorLayout.setVisibility(View.GONE);
        apiClient.getLatestTrack(Config.SNIPPET, TrackManageUtil.getPreviousTime(), TrackManageUtil.getCurrentTime(), Config.SEARCH_APIKEY);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                apiClient.getLatestTrack(Config.SNIPPET, TrackManageUtil.getPreviousTime(), TrackManageUtil.getCurrentTime(), Config.SEARCH_APIKEY);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eventSnippet(TrackEvent.OnLatestSnippetEvent event) {
        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            getTrackDuration(snippetItems);
        } else {
            progress.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void eventTrack(TrackEvent.OnLatestTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTrackList = trackManageUtil.getMostPopuTrackList(snippetItems, trackItems, token);
                if (myTrackList.size() > 0) {
                    EventBus.getDefault().postSticky(new TrackEvent.OnTrackSyncEvent(true, myTrackList));
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public void getTrackDuration(List<MostTrackItem> items) {
        StringBuilder sb = new StringBuilder();
        if (items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                if (!TextUtils.isEmpty(items.get(i).getId())) {
                    sb.append(items.get(i).getId() + ",");
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                apiClient.getLatestTrackDuration(Config.CONTENTDETAIL +","+Config.STATISTICS, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }
}
