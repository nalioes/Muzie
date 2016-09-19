package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.activities.SyncsTrackActivity;
import com.syncsource.org.muzie.model.MyTrack;

import java.util.List;

/**
 * Created by SyncSource on 9/18/2016.
 */
public class TrackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyTrack> myTracks;
    private Context context;
    private boolean isFooterEnabled = true;
    private final int VIEW_ITEM = 0;
    private final int VIEW_LOADING = 1;

    public TrackAdapter(Context context, List<MyTrack> myTracks) {
        this.context = context;
        this.myTracks = myTracks;
        notifyDataSetChanged();
    }

    public void addTrackItem(Context context, List<MyTrack> myTracksList) {
        this.context = context;
        this.myTracks = myTracksList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView trackImage;
        TextView trackTitle;
        TextView durationTime;
        TextView channelTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            trackImage = (ImageView) itemView.findViewById(R.id.trackImage);
            trackTitle = (TextView) itemView.findViewById(R.id.trackTitle);
            durationTime = (TextView) itemView.findViewById(R.id.trackDuration);
            channelTitle = (TextView) itemView.findViewById(R.id.channelTitle);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track, parent, false);
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
            Glide.with(context)
                    .load(myTrack.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(((ImageViewHolder) holder).trackImage);
            ((ImageViewHolder) holder).trackTitle.setText(myTrack.getTitle());
            ((ImageViewHolder) holder).durationTime.setText("04:30");
            ((ImageViewHolder) holder).channelTitle.setText(myTrack.getChannelTitle());

            ((ImageViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SyncsTrackActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(SyncsTrackActivity.SYNCID, myTrack);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (isFooterEnabled) ? myTracks.size() + 1 : myTracks.size();
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p/>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
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