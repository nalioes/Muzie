package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class TrackID {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo;
    @SerializedName("items")
    @Expose
    private List<TrackItem> items = new ArrayList<TrackItem>();

    public TrackID() {
    }

    public TrackID(String kind, String etag, PageInfo pageInfo, List<TrackItem> items) {
        this.kind = kind;
        this.etag = etag;
        this.pageInfo = pageInfo;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<TrackItem> getItems() {
        return items;
    }
}
