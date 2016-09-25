package com.syncsource.org.muzie.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.TrackAdapter;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.MostTrackItem;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;
import com.syncsource.org.muzie.rests.ApiClient;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    private RecyclerView recyclerView;
    ApiClient apiClient;
    private LinearLayoutManager layoutManager;
    TrackAdapter adapter;
    private List<MyTrack> myTracks;
    private List<MyTrack> myTracksList;
    private List<MostTrackItem> snippetItems = new ArrayList<>();
    private List<TrackItem> trackItems = new ArrayList<>();
    private String token;
    Parcelable recyclerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.latestTrackRecycler);
        apiClient = ApiClient.getApiClientInstance();

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
        recyclerView.addOnScrollListener(recyclerViewScroller);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.onActionViewCollapsed();
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);
        int plateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View view = searchView.findViewById(plateId);

        if (view != null) {
            view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            int searchTextId = view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) view.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setFocusable(false);
                searchText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        recyclerState = layoutManager.onSaveInstanceState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerState != null) {
            layoutManager.onRestoreInstanceState(recyclerState);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void myTrackEvent(TrackEvent.OnTrackSyncEvent event) {
        if (event.isSuccess()) {
            myTracks = event.getMyTrack();
            adapter = new TrackAdapter(getApplicationContext(), myTracks);
            recyclerView.setAdapter(adapter);
        }
    }

    private RecyclerView.OnScrollListener recyclerViewScroller = new RecyclerView.OnScrollListener() {
        private int totalItem, visibleItemCount, firstVisibleItem;
        private boolean loading = true;
        private int visibleThreshold = 1;
        private int previousTotal = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItem = layoutManager.getItemCount();
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItem > previousTotal + 1) {
                    loading = false;
                    previousTotal = totalItem;
                }
            }

            if (myTracks.size() == Config.TOTAL_ITEM) {
                loading = false;
                adapter.enableFooter(false);
            }

            if (!loading && (totalItem - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && myTracks.size() != Config.TOTAL_ITEM) {
                apiClient.getNextLatestTrack(myTracks.get(adapter.getItemCount() - 3).getNextToken(), Config.SNIPPET, TrackManageUtil.getPreviousTime(), TrackManageUtil.getCurrentTime(), Config.SEARCH_APIKEY);
                loading = true;
            }
        }
    };

    @Subscribe
    public void eventSnippet(TrackEvent.OnNextLatestSnippetEvent event) {
        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            getTrackDuration(snippetItems);
        }
    }

    @Subscribe
    public void eventTrack(TrackEvent.OnNextLatestTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTracksList = trackManageUtil.getMostPopuTrackList(snippetItems, trackItems, token);
                if (myTracksList.size() > 0) {
                    for (int i = 0; i < myTracksList.size(); i++) {
                        myTracks.add(myTracksList.get(i));
                    }
                    adapter.addTrackItem(getApplicationContext(), myTracks);

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
                apiClient.getNextLatestTrackDuration(Config.CONTENTDETAIL + "," + Config.STATISTICS, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }
}
