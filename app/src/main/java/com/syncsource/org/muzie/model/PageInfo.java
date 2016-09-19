package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/3/2016.
 */
public class PageInfo {
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("resultsPerPage")
    @Expose
    private Integer resultsPerPage;

    public PageInfo() {
    }

    public PageInfo(Integer totalResults, Integer resultsPerPage) {
        this.totalResults = totalResults;
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }
}
