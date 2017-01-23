package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nls on 1/23/17.
 */

public class QueryScTrack {
    @SerializedName("collection")
    @Expose
    private List<QueryCollectionTrack> collection = null;
    @SerializedName("next_href")
    @Expose
    private String nextHref;

    public List<QueryCollectionTrack> getCollection() {
        return collection;
    }

    public String getNextHref() {
        return nextHref;
    }
}
