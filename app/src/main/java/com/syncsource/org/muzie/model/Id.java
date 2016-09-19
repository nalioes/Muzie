package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/3/2016.
 */
public class Id {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    public Id() {
    }

    public Id(String kind, String videoId) {
        this.kind = kind;
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public String getVideoId() {
        return videoId;
    }
}
