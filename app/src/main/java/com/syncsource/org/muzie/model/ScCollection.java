package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nls on 1/23/17.
 */

public class ScCollection {
    @SerializedName("track")
    @Expose
    private ScTrack track;
    @SerializedName("score")
    @Expose
    private Integer score;

    public ScTrack getTrack() {
        return track;
    }

    public Integer getScore() {
        return score;
    }
}
