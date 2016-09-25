package com.syncsource.org.muzie;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by SyncSource on 9/21/2016.
 */

public class ImageBindingAdapter {
    @BindingAdapter("bind:imageUrl")
    public static void bindImage(ImageView imageView,String path){
        Glide.with(imageView.getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
    }
}
