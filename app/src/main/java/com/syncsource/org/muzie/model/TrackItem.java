package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class TrackItem {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;

    public TrackItem() {
    }

    public TrackItem(String kind, String etag, String id, ContentDetails contentDetails) {
        this.kind = kind;
        this.etag = etag;
        this.id = id;
        this.contentDetails = contentDetails;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getId() {
        return id;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }
}
