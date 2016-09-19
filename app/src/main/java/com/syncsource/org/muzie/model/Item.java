package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/1/2016.
 */
public class Item {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public Item() {
    }

    public Item(String kind, String etag, Id id, Snippet snippet) {
        this.kind = kind;
        this.etag = etag;
        this.id = id;
        this.snippet = snippet;

    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public Id getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

}
