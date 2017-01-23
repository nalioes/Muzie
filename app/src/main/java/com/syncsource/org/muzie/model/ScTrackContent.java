package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nalioes on 1/14/17.
 */

public class ScTrackContent {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("collection")
    @Expose
    private List<ScCollection> collection = null;
    @SerializedName("query_urn")
    @Expose
    private String queryUrn;
    @SerializedName("next_href")
    @Expose
    private String nextHref;
}
