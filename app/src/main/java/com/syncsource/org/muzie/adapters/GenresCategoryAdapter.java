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
import com.syncsource.org.muzie.databinding.ItemGenresCategoryBinding;
import com.syncsource.org.muzie.model.ScGenreCategory;
import com.syncsource.org.muzie.model.ScTrackContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nls on 1/24/17.
 */

public class GenresCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ScGenreCategory> genresCategory = new ArrayList<>();

    public GenresCategoryAdapter(Context context) {
        this.context = context;
    }

    public void addNewItemView(List<ScGenreCategory> genresCategory) {
        this.genresCategory = genresCategory;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genres_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).getBinding().genresCategoryTitle.setText(genresCategory.get(position).getTitle());
        Glide.with(context)
                .load(genresCategory.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ItemViewHolder) holder).getBinding().backgroundImage);
    }

    @Override
    public int getItemCount() {
        return genresCategory.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemGenresCategoryBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemGenresCategoryBinding getBinding() {
            return binding;
        }
    }
}
