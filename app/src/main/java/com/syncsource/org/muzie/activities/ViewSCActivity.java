package com.syncsource.org.muzie.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.SCAdapter;

import com.syncsource.org.muzie.databinding.ActivityViewScBinding;
import com.syncsource.org.muzie.model.SCMusic;
import com.syncsource.org.muzie.utils.MediaObjs;

import java.util.ArrayList;
import java.util.List;

public class ViewSCActivity extends AppCompatActivity {
    public static final String POSTMEDIA = "post_media";

    ActivityViewScBinding binding;
    List<SCMusic> scMusic = new ArrayList<>();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ViewSCActivity.this, R.layout.activity_view_sc);

        if (getIntent().hasExtra(POSTMEDIA)) {
            MediaObjs mediaObj = (MediaObjs) getIntent().getSerializableExtra(POSTMEDIA);

            scMusic = mediaObj.getScMusics();
            SCAdapter adapter = new SCAdapter(getApplicationContext(), scMusic, mediaObj.getPosition());
            binding.viewPager.setAdapter(adapter);
            binding.viewPager.setCurrentItem(mediaObj.getPosition());
        }


    }

}
