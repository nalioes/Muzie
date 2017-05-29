package com.syncsource.org.muzie.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syncsource.org.muzie.MuzieApp;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.TopAdapter;
import com.syncsource.org.muzie.databinding.FragmentTopBinding;
import com.syncsource.org.muzie.events.ScTrackEvent;
import com.syncsource.org.muzie.model.SCMusic;
import com.syncsource.org.muzie.rests.ScApiClient;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalioes on 4/30/17.
 */

public class TopFragment extends Fragment {
    FragmentTopBinding binding;
    ScApiClient scApiClient;
    TopAdapter topAdapter;
    List<SCMusic> topMusics = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topAdapter = new TopAdapter(getActivity().getApplicationContext());
        scApiClient = ScApiClient.getApiClientInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top, container, false);
        binding.load.setVisibility(View.VISIBLE);

        if (MuzieApp.getTopTrackEvent() != null) {
            bindData(MuzieApp.getTopTrackEvent());
        } else {
            scApiClient.getTopPopularTrack();
        }
        binding.topTracksRecycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.topTracksRecycle.setHasFixedSize(true);
        binding.topTracksRecycle.setNestedScrollingEnabled(false);
        binding.topTracksRecycle.scrollToPosition(0);
        binding.topTracksRecycle.setAdapter(topAdapter);

        return binding.getRoot();
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
    public void getTopItem(final ScTrackEvent.OnTopTrackEvent topTrack) {
        bindData(topTrack);
    }

    public void bindData(ScTrackEvent.OnTopTrackEvent topTrack) {
        if (topTrack.isSuccess()) {
            binding.load.setVisibility(View.GONE);
            MuzieApp.setTopTrackEvent(topTrack);
            topMusics = TrackManageUtil.getScTopMusicList(topTrack.getItem());
            if (topMusics.size() > 0) {
                topAdapter.addMoreItem(topMusics);
            }
        }
    }
}
