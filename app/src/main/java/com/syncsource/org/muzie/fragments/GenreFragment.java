package com.syncsource.org.muzie.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.FragmentGenreBinding;

/**
 * Created by nalioes on 4/30/17.
 */

public class GenreFragment extends Fragment {
    FragmentGenreBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false);
        return binding.getRoot();
    }
}
