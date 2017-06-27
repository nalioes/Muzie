package com.syncsource.org.muzie.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.NewHotAdapter;
import com.syncsource.org.muzie.databinding.FragmentNewHotBinding;
import com.syncsource.org.muzie.events.ScTrackEvent;
import com.syncsource.org.muzie.model.Filter;
import com.syncsource.org.muzie.model.SCMusic;
import com.syncsource.org.muzie.rests.ScApiClient;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalioes on 4/30/17.
 */

public class NewAndHotFragment extends Fragment {
    FragmentNewHotBinding binding;
    List<SCMusic> newHotList = new ArrayList<>();
    NewHotAdapter newHotAdapter;
    ScApiClient scApiClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scApiClient = ScApiClient.getApiClientInstance();
        scApiClient.getNewHotTrack();
        newHotAdapter = new NewHotAdapter(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_hot, container, false);
        binding.loadProgress.setVisibility(View.VISIBLE);
        binding.newHotRecycle.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.newHotRecycle.setHasFixedSize(true);
        binding.newHotRecycle.setNestedScrollingEnabled(false);
        binding.newHotRecycle.scrollToPosition(0);
        binding.newHotRecycle.setAdapter(newHotAdapter);

        return binding.getRoot();
    }

    @Subscribe
    public void getNewHotItem(final ScTrackEvent.OnNewHotTrackEvent newHot) {
        if (newHot.isSuccess()) {
            newHotList = TrackManageUtil.getScTopMusicList(newHot.getItem());
            if (newHotList.size() > 0) {
                newHotAdapter.addMoreData(newHotList);
                binding.loadProgress.setVisibility(View.GONE);
            }
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

    public void setData(Filter filter) {
        scApiClient.getNewHotGenresTrack(filter.getValue(), Config.SC_MAX_NUMBER);
        binding.newHotRecycle.setVisibility(View.GONE);
        binding.loadProgress.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void getGenersData(ScTrackEvent.OnNewHotGeneresEvent event) {
        if (event.isSuccess()) {
            binding.loadProgress.setVisibility(View.GONE);
            binding.newHotRecycle.setVisibility(View.VISIBLE);
            if (TrackManageUtil.getScTopMusicList(event.getItem()).size() > 0) {
                newHotAdapter.addMoreData(TrackManageUtil.getScTopMusicList(event.getItem()));
            }
        }
    }
}
