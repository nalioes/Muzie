package com.syncsource.org.muzie.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.activities.SearchActivity;
import com.syncsource.org.muzie.adapters.TrackAdapter;
import com.syncsource.org.muzie.analytics.AnalyticsManager;
import com.syncsource.org.muzie.databinding.FragmentYouTubeBinding;
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

/**
 * Created by nls on 1/23/17.
 */

public class MyYtubeFragment extends Fragment {
    FragmentYouTubeBinding binding;
    private LinearLayoutManager layoutManager;
    private TrackAdapter adapter;
    ApiClient apiClient;
    private List<MyTrack> myTracks = new ArrayList<>();
    Parcelable recyclerState;
    private List<MyTrack> myTracksList;
    private List<MostTrackItem> snippetItems = new ArrayList<>();
    private List<TrackItem> trackItems = new ArrayList<>();
    private String token;
    TrackIntetface trackIntetface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = ApiClient.getApiClientInstance();
        adapter = new TrackAdapter(getActivity().getApplicationContext());
        if (trackIntetface != null) {
            myTracks = trackIntetface.getMyTrack();
            adapter.addTrackItem(getActivity().getApplicationContext(), myTracks);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trackIntetface = (TrackIntetface) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_you_tube, container, false);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.latestTrackRecycler.setLayoutManager(layoutManager);
        binding.latestTrackRecycler.setHasFixedSize(true);
        binding.latestTrackRecycler.setNestedScrollingEnabled(false);
        binding.latestTrackRecycler.scrollToPosition(0);
        binding.latestTrackRecycler.addOnScrollListener(recyclerViewScroller);

        binding.mSearchBar.searchView.onActionViewCollapsed();
        binding.mSearchBar.searchView.clearFocus();
        binding.mSearchBar.searchView.setIconifiedByDefault(false);
        int plateId = binding.mSearchBar.searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View view = binding.mSearchBar.searchView.findViewById(plateId);

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
                        Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                });
            }
        }

        binding.latestTrackRecycler.setAdapter(adapter);

        binding.mSearchBar.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerState = layoutManager.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsManager.getObjInstance().sendScreenView(getString(R.string.popular_music_screen));
        if (recyclerState != null) {
            layoutManager.onRestoreInstanceState(recyclerState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
                    adapter.addTrackItem(getActivity().getApplicationContext(), myTracks);

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

    public interface TrackIntetface {
        List<MyTrack> getMyTrack();
    }

    public void onReceivedYT(boolean result) {
        if (result) {

        }
    }
}
