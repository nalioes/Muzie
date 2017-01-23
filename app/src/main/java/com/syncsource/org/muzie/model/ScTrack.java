package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nls on 1/23/17.
 */

public class ScTrack {
    @SerializedName("artwork_url")
    @Expose
    private String artworkUrl;
    @SerializedName("commentable")
    @Expose
    private Boolean commentable;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("downloadable")
    @Expose
    private Boolean downloadable;
    @SerializedName("download_count")
    @Expose
    private Integer downloadCount;
    @SerializedName("download_url")
    @Expose
    private Object downloadUrl;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("full_duration")
    @Expose
    private Integer fullDuration;
    @SerializedName("embeddable_by")
    @Expose
    private String embeddableBy;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("has_downloads_left")
    @Expose
    private Boolean hasDownloadsLeft;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("label_name")
    @Expose
    private Object labelName;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("permalink_url")
    @Expose
    private String permalinkUrl;
    @SerializedName("playback_count")
    @Expose
    private Integer playbackCount;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("publisher_metadata")
    @Expose
    private PublisherMetadata publisherMetadata;
    @SerializedName("purchase_title")
    @Expose
    private String purchaseTitle;
    @SerializedName("purchase_url")
    @Expose
    private String purchaseUrl;
    @SerializedName("release_date")
    @Expose
    private Object releaseDate;
    @SerializedName("reposts_count")
    @Expose
    private Integer repostsCount;
    @SerializedName("secret_token")
    @Expose
    private Object secretToken;
    @SerializedName("sharing")
    @Expose
    private String sharing;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("streamable")
    @Expose
    private Boolean streamable;
    @SerializedName("tag_list")
    @Expose
    private String tagList;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("urn")
    @Expose
    private String urn;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("visuals")
    @Expose
    private Object visuals;
    @SerializedName("waveform_url")
    @Expose
    private String waveformUrl;
    @SerializedName("monetization_model")
    @Expose
    private String monetizationModel;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("user")
    @Expose
    private ScUser user;

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public Object getDownloadUrl() {
        return downloadUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getFullDuration() {
        return fullDuration;
    }

    public String getEmbeddableBy() {
        return embeddableBy;
    }

    public String getGenre() {
        return genre;
    }

    public Boolean getHasDownloadsLeft() {
        return hasDownloadsLeft;
    }

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public Object getLabelName() {
        return labelName;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getLicense() {
        return license;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public Integer getPlaybackCount() {
        return playbackCount;
    }

    public Boolean get_public() {
        return _public;
    }

    public PublisherMetadata getPublisherMetadata() {
        return publisherMetadata;
    }

    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public Object getReleaseDate() {
        return releaseDate;
    }

    public Integer getRepostsCount() {
        return repostsCount;
    }

    public Object getSecretToken() {
        return secretToken;
    }

    public String getSharing() {
        return sharing;
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

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getUrn() {
        return urn;
    }

    public Integer getUserId() {
        return userId;
    }

    public Object getVisuals() {
        return visuals;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public String getMonetizationModel() {
        return monetizationModel;
    }

    public String getPolicy() {
        return policy;
    }

    public ScUser getUser() {
        return user;
    }
}
