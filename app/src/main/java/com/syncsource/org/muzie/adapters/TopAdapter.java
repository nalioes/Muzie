package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.ScTrackActivity;
import com.syncsource.org.muzie.activities.SyncsTrackActivity;
import com.syncsource.org.muzie.databinding.ItemTopBinding;
import com.syncsource.org.muzie.model.SCMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalioes on 1/28/17.
 */

public class TopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SCMusic> topMusic = new ArrayList<>();

    public TopAdapter(Context context) {
        this.context = context;
    }
    public TopAdapter(Context context,List<SCMusic> topMusic) {
        this.context = context;
        this.topMusic = topMusic;
        notifyDataSetChanged();
    }
    public void addMoreItem(List<SCMusic> topMusic) {
        this.topMusic = topMusic;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_top, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SCMusic music = topMusic.get(position);
        Glide.with(context)
                .load(music.getThumbnail())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ItemViewHolder) holder).getBinding().trackImage);
        ((ItemViewHolder) holder).getBinding().artistName.setText(music.getArtist_name());
        if (!TextUtils.isEmpty(music.getLikes_count())) {
            ((ItemViewHolder) holder).getBinding().likeCount.setText(music.getLikes_count());
        } else {

        }
        if (!TextUtils.isEmpty(music.getPlayback_count())) {
            ((ItemViewHolder) holder).getBinding().playbackCount.setText(music.getPlayback_count());

        } else {

        }
        ((ItemViewHolder) holder).getBinding().title.setText(music.getTitle());
        ((ItemViewHolder) holder).getBinding().trackDuration.setText(music.getDuration());
        ((ItemViewHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScTrackActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ScTrackActivity.SCYNCID, music);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return topMusic.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemTopBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemTopBinding getBinding() {
            return binding;
        }
    }
}
