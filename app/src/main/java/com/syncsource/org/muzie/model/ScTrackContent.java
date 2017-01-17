package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nalioes on 1/14/17.
 */

public class ScTrackContent {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("last_modified")
    @Expose
    public String lastModified;
    @SerializedName("permalink")
    @Expose
    public String permalink;
    @SerializedName("permalink_url")
    @Expose
    public String permalinkUrl;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("duration")
    @Expose
    public Integer duration;
    @SerializedName("sharing")
    @Expose
    public String sharing;
    @SerializedName("waveform_url")
    @Expose
    public String waveformUrl;
    @SerializedName("stream_url")
    @Expose
    public String streamUrl;
    @SerializedName("uri")
    @Expose
    public String uri;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("artwork_url")
    @Expose
    public String artworkUrl;
    @SerializedName("comment_count")
    @Expose
    public Integer commentCount;
    @SerializedName("commentable")
    @Expose
    public Boolean commentable;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("download_count")
    @Expose
    public Integer downloadCount;
    @SerializedName("downloadable")
    @Expose
    public Boolean downloadable;
    @SerializedName("embeddable_by")
    @Expose
    public String embeddableBy;
    @SerializedName("favoritings_count")
    @Expose
    public Integer favoritingsCount;
    @SerializedName("genre")
    @Expose
    public String genre;
    @SerializedName("isrc")
    @Expose
    public String isrc;
    @SerializedName("label_id")
    @Expose
    public Integer labelId;
    @SerializedName("label_name")
    @Expose
    public String labelName;
    @SerializedName("license")
    @Expose
    public String license;
    @SerializedName("original_content_size")
    @Expose
    public Integer originalContentSize;
    @SerializedName("original_format")
    @Expose
    public String originalFormat;
    @SerializedName("playback_count")
    @Expose
    public Integer playbackCount;
    @SerializedName("purchase_title")
    @Expose
    public String purchaseTitle;
    @SerializedName("purchase_url")
    @Expose
    public String purchaseUrl;
    @SerializedName("release")
    @Expose
    public String release;
    @SerializedName("release_day")
    @Expose
    public Integer releaseDay;
    @SerializedName("release_month")
    @Expose
    public Integer releaseMonth;
    @SerializedName("release_year")
    @Expose
    public Integer releaseYear;
    @SerializedName("reposts_count")
    @Expose
    public Integer repostsCount;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("streamable")
    @Expose
    public Boolean streamable;
    @SerializedName("tag_list")
    @Expose
    public String tagList;
    @SerializedName("track_type")
    @Expose
    public String trackType;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("likes_count")
    @Expose
    public Integer likesCount;
    @SerializedName("attachments_uri")
    @Expose
    public String attachmentsUri;
    @SerializedName("bpm")
    @Expose
    public Integer bpm;
    @SerializedName("key_signature")
    @Expose
    public String keySignature;
    @SerializedName("user_favorite")
    @Expose
    public Boolean userFavorite;
    @SerializedName("user_playback_count")
    @Expose
    public Integer userPlaybackCount;
    @SerializedName("video_url")
    @Expose
    public String videoUrl;
    @SerializedName("download_url")
    @Expose
    public String downloadUrl;

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getSharing() {
        return sharing;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public String getUri() {
        return uri;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public String getEmbeddableBy() {
        return embeddableBy;
    }

    public Integer getFavoritingsCount() {
        return favoritingsCount;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsrc() {
        return isrc;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getLicense() {
        return license;
    }

    public Integer getOriginalContentSize() {
        return originalContentSize;
    }

    public String getOriginalFormat() {
        return originalFormat;
    }

    public Integer getPlaybackCount() {
        return playbackCount;
    }

    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public String getRelease() {
        return release;
    }

    public Integer getReleaseDay() {
        return releaseDay;
    }

    public Integer getReleaseMonth() {
        return releaseMonth;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Integer getRepostsCount() {
        return repostsCount;
    }

    public String getState() {
        return state;
    }

    public Boolean getStreamable() {
        return streamable;
    }

    public String getTagList() {
        return tagList;
    }

    public String getTrackType() {
        return trackType;
    }

    public User getUser() {
        return user;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public String getAttachmentsUri() {
        return attachmentsUri;
    }

    public Integer getBpm() {
        return bpm;
    }

    public String getKeySignature() {
        return keySignature;
    }

    public Boolean getUserFavorite() {
        return userFavorite;
    }

    public Integer getUserPlaybackCount() {
        return userPlaybackCount;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
