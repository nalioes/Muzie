package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.ViewSoundCloudBinding;
import com.syncsource.org.muzie.model.SCMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nls on 7/13/17.
 */

public class SCAdapter extends PagerAdapter {
    Context context;
    List<SCMusic> scMusics = new ArrayList<>();
    int pos = 0;

    public SCAdapter(Context context) {
        this.context = context;
    }

    public SCAdapter(Context context, List<SCMusic> scMusics, int pos) {
        this.context = context;
        this.scMusics = scMusics;
        this.pos = pos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scMusics.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewSoundCloudBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_sound_cloud, container, false);
//        if (pos==position){
//            SCMusic myTrack = scMusics.get(pos);
//            Glide.with(context)
//                    .load(myTrack.getThumbnail())
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(binding.scTrackImage);
//
//            binding.scTrackTitle.setText(myTrack.getTitle());
//            binding.currentDuration.setText("00:00");
//            binding.subTitle.setText(myTrack.getArtist_name());
//            binding.totalDuration.setText(myTrack.getDuration());
////            notifyDataSetChanged();
//        }else {
//            pos = position;
//            SCMusic myTrack = scMusics.get(pos);
//            Glide.with(context)
//                    .load(myTrack.getThumbnail())
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(binding.scTrackImage);
//
//            binding.scTrackTitle.setText(myTrack.getTitle());
//            binding.currentDuration.setText("00:00");
//            binding.subTitle.setText(myTrack.getArtist_name());
//            binding.totalDuration.setText(myTrack.getDuration());
////            notifyDataSetChanged();
//        }
        SCMusic myTrack = scMusics.get(position);
        Glide.with(context)
                .load(myTrack.getThumbnail())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.scTrackImage);

        binding.scTrackTitle.setText(myTrack.getTitle());
        binding.currentDuration.setText("00:00");
        binding.subTitle.setText(myTrack.getArtist_name());
        binding.totalDuration.setText(myTrack.getDuration());


        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
