package com.syncsource.org.muzie.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.SearchAdapter;
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


/**
 * Created by SyncSource on 8/28/2016.
 */
public class SearchResultFragment extends Fragment {

    private static final String SEARCH_CONTENT = "search_content";
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<MyTrack> myTracks;
    private List<MyTrack> myTrackList;
    private List<Item> snippetItems = new ArrayList<>();
    private List<TrackItem> trackItems = new ArrayList<>();
    MyTrackDataInterface trackDataInterface;
    private LinearLayoutManager layoutManager;
    private int currentPage = 1;
    ApiClient apiClient;
    private String searchContent;
    private String token;

    public static SearchResultFragment getFragment(String content) {
        SearchResultFragment searchFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_CONTENT, content);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (trackDataInterface != null) {
            myTracks = trackDataInterface.getTracks();
            adapter = new SearchAdapter(getActivity().getApplicationContext(), myTracks);

        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            searchContent = bundle.getString(SEARCH_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler);
        apiClient = ApiClient.getApiClientInstance();

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(recyclerViewScroller);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trackDataInterface = (MyTrackDataInterface) context;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public interface MyTrackDataInterface {
        List<MyTrack> getTracks();
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

            if (myTracks.size() == Config.TOTAL_ITEM){
                loading = false;
                adapter.enableFooter(false);
            }

            if (!loading && (totalItem - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && myTracks.size() != Config.TOTAL_ITEM) {
                apiClient.getNextTrackID(myTracks.get(adapter.getItemCount() - 3).getNextToken(), Config.SNIPPET, searchContent, Config.MAX_NUMBER, Config.SEARCH_APIKEY);
                loading = true;
            }
        }
    };

    @Subscribe
    protected void eventSnippet(TrackEvent.OnSnippetTrackEvent event) {
        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            getTrackDuration(snippetItems);
        }
    }

    @Subscribe
    protected void eventTrack(TrackEvent.OnNextTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTrackList = trackManageUtil.getTrackList(snippetItems, trackItems, token);
                if (myTrackList.size() > 0) {
                    for (int i = 0; i < myTrackList.size(); i++) {
                        myTracks.add(myTrackList.get(i));
                    }
                    adapter.addTrack(getActivity().getApplicationContext(), myTracks);
                    recyclerView.getRecycledViewPool().clear();
                    adapter.notifyDataSetChanged();
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
                apiClient.getNextTrackDuration(Config.CONTENTDETAIL, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }

}
