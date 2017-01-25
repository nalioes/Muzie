package com.syncsource.org.muzie.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.GenresCategoryAdapter;
import com.syncsource.org.muzie.analytics.AnalyticsManager;
import com.syncsource.org.muzie.databinding.FragmentSCloudBinding;
import com.syncsource.org.muzie.events.ScTrackEvent;
import com.syncsource.org.muzie.model.ScGenreCategory;
import com.syncsource.org.muzie.model.ScTrackContent;
import com.syncsource.org.muzie.rests.ScApiClient;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nls on 1/23/17.
 */

public class MyScloudFragment extends Fragment {
    FragmentSCloudBinding binding;
    private LinearLayoutManager layoutManager;
    ScApiClient apiClient;
    Parcelable recyclerState;
    List<ScGenreCategory> genreCategoryList = new ArrayList<>();
    List<ScTrackContent> newHotList = new ArrayList<>();
    List<ScTrackContent> topTrackList = new ArrayList<>();
    GenresCategoryAdapter genresCategoryAdapter;
    public static String[] genreArray;
    public static int genreIndex = 0;
    final public static int itemByCategory = 1;
    SCInterface scListener;

    public static MyScloudFragment scNewInstance() {
        MyScloudFragment fragment = new MyScloudFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        scListener = (SCInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = ScApiClient.getApiClientInstance();
        genreArray = getActivity().getApplicationContext().getResources().getStringArray(R.array.music_genres);
        genresCategoryAdapter = new GenresCategoryAdapter(getActivity().getApplicationContext());
        if (scListener != null) {
            Toast.makeText(getActivity(), "SC", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_cloud, container, false);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.genreRecycle.setLayoutManager(layoutManager);
        binding.genreRecycle.setHasFixedSize(true);
        binding.genreRecycle.setNestedScrollingEnabled(false);
        binding.genreRecycle.scrollToPosition(0);
        binding.genreRecycle.setAdapter(genresCategoryAdapter);

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
        AnalyticsManager.getObjInstance().sendScreenView("SoundCloud main Screen");
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

    @Subscribe
    public void getTopGenres(ScTrackEvent.OnTopGenresTrackEvent topGenres) {

        if (genreIndex + 1 != genreArray.length) {
            apiClient.getTopGenresTrack(genreArray[++genreIndex], itemByCategory);
        }

        if (topGenres.isSuccess()) {
            if (topGenres.getItem().getCollection().size() > 0) {
                if (TrackManageUtil.getGenreCategory(topGenres.getItem().getCollection().get(0).getTrack()) != null) {
                    ScGenreCategory scGenreCategory = TrackManageUtil.getGenreCategory(topGenres.getItem().getCollection().get(0).getTrack());
                    scGenreCategory.setQueryGenre(topGenres.getItem().getGenre());
                    genreCategoryList.add(scGenreCategory);
                    genresCategoryAdapter.addNewItemView(genreCategoryList);
                }

            }

        }
    }

    public interface SCInterface {
        boolean scReceived();
    }
}
