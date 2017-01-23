package com.syncsource.org.muzie.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.FragmentSCloudBinding;

/**
 * Created by nls on 1/23/17.
 */

public class MyScloudFragment extends Fragment {
    FragmentSCloudBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_cloud, container, false);
        return binding.getRoot();
    }
}
