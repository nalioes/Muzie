package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.ItemNewHotBinding;
import com.syncsource.org.muzie.model.SCMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalioes on 1/27/17.
 */

public class NewHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SCMusic> newHotItem = new ArrayList<>();

    public NewHotAdapter(Context context) {
        this.context = context;
    }

    public void addMoreData(List<SCMusic> newHotItem) {
        this.newHotItem = newHotItem;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_new_hot, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SCMusic music = newHotItem.get(position);
        Glide.with(context)
                .load(music.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ItemViewHolder) holder).getBinding().backgroundImage);

        ((ItemViewHolder) holder).getBinding().artistName.setText(music.getArtist_name());
        ((ItemViewHolder) holder).getBinding().title.setText(music.getTitle());
        ((ItemViewHolder) holder).getBinding().trackDuration.setText(music.getDuration());
    }

    @Override
    public int getItemCount() {
        return newHotItem.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemNewHotBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemNewHotBinding getBinding() {
            return binding;
        }
    }
}
