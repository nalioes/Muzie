package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.syncsource.org.muzie.BR;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.activities.SyncsTrackActivity;
import com.syncsource.org.muzie.databinding.PopularTrackBinding;
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
            ((ImageViewHolder) holder).getBinding().setVariable(BR.track, myTrack);
            ((ImageViewHolder) holder).getBinding().executePendingBindings();
            ((ImageViewHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
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
        if (!isFooterEnabled) {
            return myTracks.size();
        } else {
            if (myTracks.size() == 30) {
                return myTracks.size();
            } else {
                return myTracks.size() + 1;
            }
        }
    }

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