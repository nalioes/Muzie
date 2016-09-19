package com.syncsource.org.muzie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.Item;
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
    private List<Item> snippetItems = new ArrayList<>();
    private String token;
    private List<TrackItem> trackItems = new ArrayList<>();
    private List<MyTrack> myTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        apiClient = ApiClient.getApiClientInstance();
        apiClient.getLatestTrack(Config.SNIPPET, TrackManageUtil.getPreviousTime(), TrackManageUtil.getCurrentTime(), Config.SEARCH_APIKEY);

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
    protected void eventSnippet(TrackEvent.OnLatestSnippetEvent event) {
        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            getTrackDuration(snippetItems);
        }
    }

    @Subscribe
    protected void eventTrack(TrackEvent.OnLatestTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTrackList = trackManageUtil.getTrackList(snippetItems, trackItems, token);
                if (myTrackList.size() > 0) {
                    EventBus.getDefault().postSticky(new TrackEvent.OnTrackSyncEvent(true, myTrackList));
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }


    public void getTrackDuration(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        if (items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                if (!TextUtils.isEmpty(items.get(i).getId().getVideoId())) {
                    sb.append(items.get(i).getId().getVideoId() + ",");
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                apiClient.getLatestTrackDuration(Config.CONTENTDETAIL, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }
}
