package com.syncsource.org.muzie.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;
import com.syncsource.org.muzie.rests.ApiClient;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    FrameLayout rootLayout;
    private RecyclerView recyclerView;
    ApiClient apiClient;
    private LinearLayoutManager layoutManager;
    TrackAdapter adapter;
    private List<MyTrack> myTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rootLayout = (FrameLayout) findViewById(R.id.base_layout);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.latestTrackRecycler);
        apiClient = ApiClient.getApiClientInstance();

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);

      //  recyclerView.addOnScrollListener(recyclerViewScroller);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.onActionViewCollapsed();
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);
        int plateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View view = searchView.findViewById(plateId);

        if (view != null) {
            view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            int searchTextId = view.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
            TextView searchText = (TextView)view.findViewById(searchTextId);
            if (searchText!= null){
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

    @Override
    protected void onResume() {
        super.onResume();
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
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void myTrackEvent(TrackEvent.OnTrackSyncEvent event) {
        if (event.isSuccess()) {
            myTracks = event.getMyTrack();
            adapter = new TrackAdapter(getApplicationContext(),myTracks);
            recyclerView.setAdapter(adapter);
        }
    }
}
