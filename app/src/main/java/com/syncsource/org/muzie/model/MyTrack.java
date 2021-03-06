package com.syncsource.org.muzie.model;

import android.databinding.BaseObservable;
import java.io.Serializable;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class MyTrack extends BaseObservable implements Serializable{
    private String videoID;
    private String title;
    private String description;
    private String duration;
    private String thumbnail;
    private String artistName;
    private String nextToken;
    private String channelTitle;
    private String viewCount;
    private boolean isYouTube = false;
    private boolean isSoundCloud = false;
    private String streamUrl;
    private String genreType;
    private int likeCount;

    public MyTrack() {
    }

    public MyTrack(String videoID, String title, String description, String duration, String thumbnail, String artistName, String token ,String channelTitle) {
        this.videoID = videoID;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.artistName = artistName;
        nextToken = token;
        this.channelTitle = channelTitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public boolean isYouTube() {
        return isYouTube;
    }

    public void setYouTube(boolean youTube) {
        isYouTube = youTube;
    }

    public boolean isSoundCloud() {
        return isSoundCloud;
    }

    public void setSoundCloud(boolean soundCloud) {
        isSoundCloud = soundCloud;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
