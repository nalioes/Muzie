package com.syncsource.org.muzie.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.syncsource.org.muzie.BuildConfig;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.analytics.AnalyticsManager;
import com.syncsource.org.muzie.databinding.ActivitySearchBinding;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.fragments.ProgressFragment;
import com.syncsource.org.muzie.fragments.SearchResultFragment;
import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;
import com.syncsource.org.muzie.rests.ApiClient;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements SearchResultFragment.MyTrackDataInterface {

    ActivitySearchBinding binding;
    private ApiClient apiClient;
    private List<Item> snippetItems = new ArrayList<>();
    private List<TrackItem> trackItems = new ArrayList<>();
    private List<MyTrack> myTracks = new ArrayList<>();
    SearchView searchView;
    TextView searchText;
    FrameLayout baseLayoutContainer;
    final int myColor = 0xffffff;
    Drawable drawable;
    private String token;
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        baseLayoutContainer = (FrameLayout) binding.searchContentContainer;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.chevron_left));
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }

        drawable = new ColorDrawable(myColor);
        apiClient = ApiClient.getApiClientInstance();
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.onActionViewCollapsed();
        searchView.setIconifiedByDefault(false);

        int plateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View view = searchView.findViewById(plateId);

        if (view != null) {
            view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            int searchTextId = view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            searchText = (TextView) view.findViewById(searchTextId);
            searchText.setTextColor(getResources().getColor(R.color.white));
            searchText.setHintTextColor(getResources().getColor(R.color.white));
            if (searchText != null) {
                searchText.setFocusable(true);
            }
        }

        KeyboardVisibilityEvent.setEventListener(SearchActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {

                if (isOpen) {
                    if (searchText.getText().length() == 0) {
                        final int myColor = 0x6E080808;
                        final Drawable drawable = new ColorDrawable(myColor);
                        baseLayoutContainer.setForeground(drawable);
                        binding.searchFilterView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (baseLayoutContainer != null) {
                        binding.searchFilterView.setVisibility(View.GONE);
                        baseLayoutContainer.setForeground(drawable);
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchContent = s;
                ProgressFragment progress = new ProgressFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.search_content_container, progress)
                        .commit();
                apiClient.getTrackID(Config.SNIPPET, searchContent, Config.MAX_NUMBER, Config.SEARCH_APIKEY);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                baseLayoutContainer.setForeground(drawable);

                if (s.length() == 0) {
                    final int myColor = 0x6E080808;
                    final Drawable drawable = new ColorDrawable(myColor);
                    baseLayoutContainer.setForeground(drawable);
                    binding.searchFilterView.setVisibility(View.VISIBLE);
                } else {
                    binding.searchFilterView.setVisibility(View.GONE);
                }
                return false;
            }
        });
        binding.mSearchBar.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsManager.getObjInstance().sendScreenView(getString(R.string.search_music_screen));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds search_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void EventSnippetID(TrackEvent.OnSnippetEvent event) {

        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            trackId(snippetItems);
        }
    }


    @Subscribe
    public void EventTrackID(TrackEvent.OnTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTracks = trackManageUtil.getTrackList(snippetItems, trackItems, token);
                if (myTracks.size() > 0) {
                    SearchResultFragment search = SearchResultFragment.getFragment(searchContent);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.search_content_container, search)
                            .commit();
                }
            }
        }
    }

    public void trackId(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        if (items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                if (!TextUtils.isEmpty(items.get(i).getId().getVideoId())) {
                    sb.append(items.get(i).getId().getVideoId() + ",");
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                apiClient.getTrackDuration(Config.CONTENTDETAIL + "," + Config.STATISTICS, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }

    @Override
    public List<MyTrack> getTracks() {

        return myTracks;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
