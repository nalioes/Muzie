package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalioes on 1/14/17.
 */

public class ScTrackContent {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("collection")
    @Expose
    private List<ScCollection> collection = new ArrayList<>();
    @SerializedName("query_urn")
    @Expose
    private String queryUrn;
    @SerializedName("next_href")
    @Expose
    private String nextHref;

    public String getKind() {
        return kind;
    }

    public String getGenre() {
        return genre;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public List<ScCollection> getCollection() {
        return collection;
    }

    public String getQueryUrn() {
        return queryUrn;
    }

    public String getNextHref() {
        return nextHref;
    }
}
