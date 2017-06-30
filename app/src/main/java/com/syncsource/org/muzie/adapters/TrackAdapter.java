package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.BR;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.ScTrackActivity;
import com.syncsource.org.muzie.activities.SyncsTrackActivity;
import com.syncsource.org.muzie.databinding.PopularTrackBinding;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SyncSource on 9/18/2016.
 */
public class TrackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyTrack> myTracks = new ArrayList<>();
    private Context context;
    private boolean isFooterEnabled = true;
    private final int VIEW_ITEM = 0;
    private final int VIEW_LOADING = 1;
    int position;
    int lastPosition = -1;

    public TrackAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public void addTrackItem(Context context, List<MyTrack> myTracksList) {
        this.context = context;
//        List<MyTrack>randomTrackList = new ArrayList<>();
//        Random randomTrack = new Random();
//
//        for (int i = 0 ; i<myTracksList.size();i++){
//            int index = randomTrack.nextInt(myTracksList.size());
//            MyTrack track = myTracksList.get(index);
//            randomTrackList.add(track);
//        }

        this.myTracks = myTracksList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private PopularTrackBinding binding;

        public ImageViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public PopularTrackBinding getBinding() {
            return binding;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_track, parent, false);
            view.setVisibility(view.VISIBLE);
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress, parent, false);
            LoadViewHolder loadViewHolder = new LoadViewHolder(view);
            return loadViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LoadViewHolder) {
            ((LoadViewHolder) holder).progressBar.setIndeterminate(true);
        } else {

            final MyTrack myTrack = myTracks.get(position);
            if (myTrack.isYouTube()) {
                ((ImageViewHolder) holder).binding.channelTitle.setText(myTrack.getChannelTitle());
                ((ImageViewHolder) holder).binding.trackDuration.setText(myTrack.getDuration());
                ((ImageViewHolder) holder).binding.trackTitle.setText(myTrack.getTitle());
                ((ImageViewHolder) holder).binding.viewCount.setText(myTrack.getViewCount());
                Glide.with(context)
                        .load(myTrack.getThumbnail())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ImageViewHolder) holder).binding.trackImage);

                ((ImageViewHolder) holder).getBinding().container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SyncsTrackActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(SyncsTrackActivity.SYNCID, myTrack);
                        context.startActivity(intent);
                    }
                });

            } else {
                ((ImageViewHolder) holder).binding.channelTitle.setText(myTrack.getGenreType());
                ((ImageViewHolder) holder).binding.trackDuration.setText(myTrack.getDuration());
                ((ImageViewHolder) holder).binding.trackTitle.setText(myTrack.getTitle());

                if (myTrack.getLikeCount() > 0) {
                    ((ImageViewHolder) holder).binding.viewCount.setText(String.valueOf(myTrack.getLikeCount()));
                } else {
                    ((ImageViewHolder) holder).binding.viewCount.setText("");
                }

                Glide.with(context)
                        .load(myTrack.getThumbnail())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ImageViewHolder) holder).binding.trackImage);

                ((ImageViewHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ScTrackActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(ScTrackActivity.SCYNCID, myTrack);
                        context.startActivity(intent);
                    }
                });
            }
//            ((ImageViewHolder) holder).getBinding().setVariable(BR.track, myTrack);
//            ((ImageViewHolder) holder).getBinding().executePendingBindings();

//            if(position >lastPosition) {
//
//                Animation animation = AnimationUtils.loadAnimation(context,
//                        R.anim.up_to_buttom);
//                ((ImageViewHolder)holder).itemView.startAnimation(animation);
//                lastPosition = position;
//            }
        }
    }

    @Override
    public int getItemCount() {
        if (!isFooterEnabled) {
            return myTracks.size();
        } else {

            if (myTracks.size() == Config.TOTAL_ITEM) {
                return myTracks.size();
            } else {
                return myTracks.size() + 1;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return (isFooterEnabled && position >= myTracks.size()) ? VIEW_LOADING : VIEW_ITEM;
    }

    public void enableFooter(boolean isEnabled) {
        this.isFooterEnabled = isEnabled;
    }

    static class LoadViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.load_more);
        }
    }
}